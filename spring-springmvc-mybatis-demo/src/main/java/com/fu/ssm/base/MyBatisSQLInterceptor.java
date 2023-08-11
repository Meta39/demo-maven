package com.fu.ssm.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

/**
 * MyBatis输出日志
 */
@Slf4j
@Intercepts({@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
public class MyBatisSQLInterceptor implements Interceptor {
    private static final ObjectMapper om = new ObjectMapper();

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        //如果输出中文乱码，则需要添加Tomcat JVM参数-Dfile.encoding=UTF-8
        Object[] args = invocation.getArgs();
        if (args != null && args.length > 1) {
            MappedStatement mappedStatement = (MappedStatement) args[0];
            Object parameter = args[1];
            BoundSql boundSql = mappedStatement.getBoundSql(parameter);
            String sql = boundSql.getSql();
            log.info("params:{}\nsql:{}", om.writeValueAsString(parameter), sql);
        }
        return invocation.proceed();
    }
}