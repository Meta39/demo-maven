package com.fu.springbootdynamicdatasource.dynamicdatasource;

import com.mysql.cj.jdbc.MysqlXADataSource;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * 数据源2
 */
@Configuration
@MapperScan(value = "com.fu.springbootdynamicdatasource.mapper.mysql2", sqlSessionFactoryRef = "mySQL2SqlSessionFactory")
public class DataSourceMySQL2 {
    @Value("${spring.datasource.mysql2.mapper-locations}")
    private String mapperLocations;

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.mysql2")
    public HikariDataSource mySQL2HikariDataSource() {
        return new HikariDataSource();
    }

    @Bean
    @Primary
    public DataSource mySQL2DataSource(@Qualifier("mySQL2HikariDataSource") HikariDataSource dataSource) {
        MysqlXADataSource mysqlXaDataSource = new MysqlXADataSource();
        mysqlXaDataSource.setUrl(dataSource.getJdbcUrl());
        mysqlXaDataSource.setUser(dataSource.getUsername());
        mysqlXaDataSource.setPassword(dataSource.getPassword());

        // 将本地事务注册到创 Atomikos全局事务
        AtomikosDataSourceBean xaDataSource = new AtomikosDataSourceBean();
        xaDataSource.setXaDataSource(mysqlXaDataSource);
        xaDataSource.setUniqueResourceName("mySQL2DataSource");
        return xaDataSource;
    }

    @Bean
    @Primary
    public SqlSessionFactory mySQL2SqlSessionFactory(@Qualifier("mySQL2DataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperLocations));
        return bean.getObject();
    }

    @Bean
    @Primary
    public SqlSessionTemplate mySQL2SqlSessionTemplate(@Qualifier("mySQL2SqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
