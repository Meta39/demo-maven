package com.fu.mybatissqllog.plugin;


import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 自定义SQL插件,功能如下
 * 1:打印SQL执行时间
 * 2:打印SQL,参数自动设置到SQL中
 * 3:区别慢SQL,SQL执行时间大于5秒的SQL为红色字体，否则为黄色字体,(执行时间可以自定义)
 * </p>
 *
 * @author liekkas 2020/12/08 10:42
 */
@Intercepts({
        @Signature(type = StatementHandler.class, method = "update", args = {Statement.class,}),
        @Signature(type = StatementHandler.class, method = "query", args = {Statement.class, ResultHandler.class})
})
public final class MyBatisSqlParsingPlugin implements Interceptor {
    private static final Logger log = LoggerFactory.getLogger(MyBatisSqlParsingPlugin.class);
    private static final Long DEFAULT_TIME_OUT = 5000L;
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        long startTime = System.currentTimeMillis();
        try {
            return invocation.proceed();
        } finally {
            long endTime = System.currentTimeMillis();
            long elapsedTime = endTime - startTime;
            String sql = formatSql(invocation);
            printColorString(String.format("执行SQL:\n%s", sql), elapsedTime);
        }
    }

    /**
     * 格式化SQL及其参数
     *
     * @param invocation invocation
     * @return java.lang.String
     * @author liekkas 2020/12/08 10:43
     */
    private String formatSql(Invocation invocation) throws NoSuchFieldException, IllegalAccessException {
        //获取StatementHandler
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        //获取ParameterHandler
        ParameterHandler parameterHandler = statementHandler.getParameterHandler();
        //获取boundSql
        BoundSql boundSql = statementHandler.getBoundSql();

        Class<? extends ParameterHandler> parameterHandlerClass = parameterHandler.getClass();
        Field mappedStatementField = parameterHandlerClass.getDeclaredField("mappedStatement");
        mappedStatementField.setAccessible(true);
        MappedStatement mappedStatement = (MappedStatement) mappedStatementField.get(parameterHandler);

        // 元SQL
        String sql = boundSql.getSql();
        // 定义一个没有替换过占位符的sql，用于出异常时返回
        String sqlWithoutReplacePlaceholder = sql;

        // 输入sql字符串空判断
        if (Objects.isNull(sql) || sql.trim().isEmpty()) {
            return "";
        }

        // 不传参数的场景，直接把Sql美化一下返回出去
        Object parameterObject = parameterHandler.getParameterObject();
        List<ParameterMapping> parameterMappingList = boundSql.getParameterMappings();
        if (Objects.isNull(parameterObject) || parameterMappingList.isEmpty()) {
            return beautifySql(sql);
        }

        try {
            sql = beautifySql(handleCommonParameter(boundSql, mappedStatement));
        } catch (Exception e) {
            log.error("替换SQL异常", e);
            log.error("SQL参数：{}", parameterObject);
            // 占位符替换过程中出现异常，则返回没有替换过占位符但是格式美化过的sql
            return sqlWithoutReplacePlaceholder;
        }

        return sql;
    }

    /**
     * 美化SQL
     *
     * @param sql sql
     * @return java.lang.String
     * @author liekkas 2020/12/08 10:45
     */
    private String beautifySql(String sql) {
        sql = sql.replaceAll("[\\s\\n\\t]+", " ");
        return sql;
    }

    /**
     * 替换SQL中的?,设置sql参数
     *
     * @param boundSql boundSql
     * @param mappedStatement mappedStatement
     * @return java.lang.String
     * @author liekkas 2020/12/08 10:46
     */
    private String handleCommonParameter(BoundSql boundSql, MappedStatement mappedStatement) {

        String sql = boundSql.getSql();
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        Configuration configuration = mappedStatement.getConfiguration();
        TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();

        for (ParameterMapping parameterMapping : parameterMappings) {
            if (parameterMapping.getMode() != ParameterMode.OUT) {
                Object value;
                String propertyName = parameterMapping.getProperty();
                if (boundSql.hasAdditionalParameter(propertyName)) {
                    value = boundSql.getAdditionalParameter(propertyName);
                } else if (parameterObject == null) {
                    value = null;
                } else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                    value = parameterObject;
                } else {
                    MetaObject metaObject = configuration.newMetaObject(parameterObject);
                    value = metaObject.getValue(propertyName);
                }
                sql = replacePlaceholder(sql, value);
            }
        }
        return sql;
    }

    /**
     * 根据不同的propertyValue类型,匹配SQL?的类型并替换值
     *
     * @param sql sql
     * @param propertyValue propertyValue
     * @return java.lang.String
     * @author liekkas 2020/12/08 10:48
     */
    private String replacePlaceholder(String sql, Object propertyValue) {
        String value;
        if (Objects.isNull(propertyValue)) {
            value = "null";
            return sql.replaceFirst("\\?", value);
        }
        if (propertyValue instanceof String) {
            value = "'" + propertyValue + "'";
        } else if (propertyValue instanceof Date) {
            value = "'" + DATE_TIME_FORMATTER.format(((Date) propertyValue).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()) + "'";
        } else if (propertyValue instanceof LocalDate) {
            value = "'" + DATE_FORMATTER.format((LocalDate) propertyValue) + "'";
        } else if (propertyValue instanceof LocalDateTime) {
            value = "'" + DATE_TIME_FORMATTER.format((LocalDateTime) propertyValue) + "'";
        } else {
            value = propertyValue.toString();
        }
        return sql.replaceFirst("\\?", value);
    }

    /**
     * 根据不同的超时时间打印不同颜色的字体，若超时时间大于默认的超时时间，打印红色字体，否则打印黄色字体
     *
     * @param str Str
     * @param timeOut 超时时间
     * @author liekkas 2020/12/08 10:50
     */
    private void printColorString(String str, Long timeOut) {
        if (timeOut < DEFAULT_TIME_OUT) {
            log.info("{}", str);
        } else {
            log.error("{}", str);
        }
    }
}