package com.ysj.project.common.config;


import javax.sql.DataSource;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
@PropertySource("classpath:/config/application.properties")
public class DataBaseConfig {
	
	  @Autowired
	  private ApplicationContext applicationContext;
	
	@Bean
	@ConfigurationProperties(prefix="spring.datasource")
	public HikariConfig hikariConfig() {
		return new HikariConfig();
	}

	@Bean
	public DataSource dataSource() {
		HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig());
		return new LazyConnectionDataSourceProxy(hikariDataSource);
	}

	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
		factoryBean.setDataSource(dataSource());
		factoryBean.setTypeAliasesPackage("com.ysj.project.main.*, com.ysj.project.web.*");
		factoryBean.setMapperLocations(applicationContext.getResources("classpath*:mapper/*-mapper.xml"));

		SqlSessionFactory factory = factoryBean.getObject();
		factory.getConfiguration().setVfsImpl(SpringBootVFS.class);
		factory.getConfiguration().setMapUnderscoreToCamelCase(true);
		factory.getConfiguration().setReturnInstanceForEmptyRow(true);
		factory.getConfiguration().setJdbcTypeForNull(JdbcType.NULL);
		factory.getConfiguration().setUseGeneratedKeys(true);
		factory.getConfiguration().setDefaultFetchSize(100);
		factory.getConfiguration().setDefaultExecutorType(ExecutorType.REUSE);
		factory.getConfiguration().setDefaultStatementTimeout(3000);

		return factory;
	}

	@Bean
	public SqlSessionTemplate sqlSession(SqlSessionFactory sqlSessionFactory) throws Exception {
		 final SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory);
		return sqlSessionTemplate;
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager(dataSource());
		dataSourceTransactionManager.setNestedTransactionAllowed(true);
		return dataSourceTransactionManager;
	}
}
