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
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        ParameterHandler parameterHandler = statementHandler.getParameterHandler();
        BoundSql boundSql = statementHandler.getBoundSql();
        String sql = boundSql.getSql();
        try {
            log.info("Execute SQL:\n{}", formatSql(parameterHandler, boundSql, sql));
        } catch (Exception e) {
            log.error("SQL:{}\nformatSql Exception:", sql,e);
        }
        return invocation.proceed();
    }

    /**
     * 格式化SQL及其参数
     */
    private String formatSql(ParameterHandler parameterHandler,BoundSql boundSql,String sql) throws NoSuchFieldException, IllegalAccessException {

        Class<? extends ParameterHandler> parameterHandlerClass = parameterHandler.getClass();
        Field mappedStatementField = parameterHandlerClass.getDeclaredField("mappedStatement");
        mappedStatementField.setAccessible(true);
        MappedStatement mappedStatement = (MappedStatement) mappedStatementField.get(parameterHandler);

        sql = sql.replaceAll("\\s+", " ");

        // 输入sql字符串空判断
        if (!StringUtils.hasText(sql)) {
            return "";
        }

        // 不传参数的场景，直接把Sql美化一下返回出去
        Object parameterObject = parameterHandler.getParameterObject();
        List<ParameterMapping> parameterMappingList = boundSql.getParameterMappings();
        if (Objects.isNull(parameterObject) || parameterMappingList.isEmpty()) {
            return sql;
        }

        return handleCommonParameter(boundSql, mappedStatement);
    }

    //替换预编译SQL
    private String handleCommonParameter(BoundSql boundSql, MappedStatement mappedStatement) {
        String sql = boundSql.getSql();
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        Configuration configuration = mappedStatement.getConfiguration();
        TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
        List<String> params = new ArrayList<>();

        for (ParameterMapping parameterMapping : parameterMappings) {
            if (parameterMapping.getMode() == ParameterMode.OUT) {
                continue;
            }
            Object propertyValue;
            String propertyName = parameterMapping.getProperty();
            if (boundSql.hasAdditionalParameter(propertyName)) {
                propertyValue = boundSql.getAdditionalParameter(propertyName);
            } else if (parameterObject == null) {
                params.add("null");
                continue;
            } else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                propertyValue = parameterObject;
            } else {
                MetaObject metaObject = configuration.newMetaObject(parameterObject);
                propertyValue = metaObject.getValue(propertyName);
            }
            //把参数放入list
            if (propertyValue instanceof String) {
                params.add("'" + propertyValue + "'");
            } else if (propertyValue instanceof Date) {
                params.add("'" + DATE_TIME_FORMATTER.format(((Date) propertyValue).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()) + "'");
            } else if (propertyValue instanceof LocalDate) {
                params.add("'" + DATE_FORMATTER.format((LocalDate) propertyValue) + "'");
            } else if (propertyValue instanceof LocalDateTime) {
                params.add("'" + DATE_TIME_FORMATTER.format((LocalDateTime) propertyValue) + "'");
            } else {
                params.add(propertyValue.toString());
            }
        }

        //转译百分号
        if (sql.contains("%")) {
            sql = sql.replaceAll("%", "%%");
        }

        sql = sql.replaceAll("\\?", "%s");
        return String.format(sql, params.toArray());
    }

}