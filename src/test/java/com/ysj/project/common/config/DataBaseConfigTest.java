package com.ysj.project.common.config;

import java.util.stream.Stream;

import javax.sql.DataSource;

import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.transaction.PlatformTransactionManager;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@SpringBootTest
@Configuration(proxyBeanMethods = false)
public class DataBaseConfigTest {
	

	@Bean
	@ConfigurationProperties(prefix = "spring.datasource.hikari")
	@Test
	public HikariConfig hikariConfig() {
		return new HikariConfig();
	}

	@Bean
	public DataSource dataSource() {
		HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig());
		return new LazyConnectionDataSourceProxy(hikariDataSource);
	}

	@Bean
	public SqlSessionFactory sqlSessionFactory(DatabaseIdProvider databaseIdProvider) throws Exception {
		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
		factoryBean.setDataSource(dataSource());
		factoryBean.setTypeAliasesPackage("com.ysj.project.main.*, com.ysj.project.web.*");
		factoryBean.setDatabaseIdProvider(databaseIdProvider);

		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		Resource[] commonResource = resolver.getResources("classpath*:mapper/common/*-mapper.xml");
		Resource[] mainResource = resolver.getResources("classpath*:mapper/main/*-mapper.xml");
		Resource[] webResource = resolver.getResources("classpath*:mapper/web/**/*-mapper.xml");
		Resource[] subResource = null;

		Resource[] resource = Stream.of(commonResource, mainResource, webResource, subResource).flatMap(Stream::of).toArray(Resource[]::new);

		factoryBean.setMapperLocations(resource);

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
	@Test
	public SqlSessionTemplate sqlSession(@Qualifier("databaseIdProvider") DatabaseIdProvider databaseIdProvider) throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory(databaseIdProvider));
	}

	@Bean
	@Test
	public PlatformTransactionManager transactionManager(@Qualifier("dataSource") DataSource dataSource) {
		DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager(dataSource);
		dataSourceTransactionManager.setNestedTransactionAllowed(true);
		return dataSourceTransactionManager;
	}
}
