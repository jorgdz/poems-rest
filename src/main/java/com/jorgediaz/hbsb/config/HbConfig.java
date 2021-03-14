package com.jorgediaz.hbsb.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class HbConfig {
	
	@Autowired
	Environment env;
	
	@Bean
    public LocalSessionFactoryBean sessionFactory () {
        LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource());
        sessionFactoryBean.setPackagesToScan("com.jorgediaz.hbsb.entity");
        sessionFactoryBean.setHibernateProperties(hibernateProperties());

        return sessionFactoryBean;
    }
	
	@Bean
    public DataSource dataSource () {
		DriverManagerDataSource dataSourceManager = new DriverManagerDataSource();
		if (this.env.getProperty("APP_TEST") != null) {
			dataSourceManager.setDriverClassName(this.env.getProperty("DB_DRIVER"));
			dataSourceManager.setUrl(this.env.getProperty("DB_URL"));
			dataSourceManager.setUsername(this.env.getProperty("DB_USER"));
			dataSourceManager.setPassword(this.env.getProperty("DB_PASS"));
		} else {
			dataSourceManager.setDriverClassName("org.h2.Driver");
			dataSourceManager.setUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
			dataSourceManager.setUsername("sa");
		}
	
        return dataSourceManager;
    }
	
	@Bean
    public PlatformTransactionManager hibernateTransactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }
	
	public Properties hibernateProperties () {
		Properties properties = new Properties();
		
		if (this.env.getProperty("APP_TEST") != null) {
			properties.setProperty("hibernate.dialect", this.env.getProperty("DB_DIALECT"));
		} else {
			properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
			properties.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
		}
		
		properties.setProperty("hibernate.show_sql", "true");	
		return properties;
	}
}
