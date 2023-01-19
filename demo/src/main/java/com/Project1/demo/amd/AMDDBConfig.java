package com.Project1.demo.amd;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.HashMap;

//@Primary
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "amdEntityManagerFactory", transactionManagerRef = "amdTransactionManager", basePackages = {"com/Project1/demo/amd"})
public class AMDDBConfig {
    //@Bean(name="amdDataSource")
    //@ConfigurationProperties(prefix="spring.aapl")
    //@Primary
    @Bean
    @ConfigurationProperties(prefix="amd.datasource")
    public DataSource amdDataSource() {
        return DataSourceBuilder.create().build();
    }



    @Bean(name = "amdEntityManagerFactory")
    //@Bean
    //public LocalContainerEntityManagerFactoryBean aaplEntityManagerFactory(EntityManagerFactoryBuilder builder, @Qualifier("aaplDataSource") DataSource aaplDataSource) {
    public LocalContainerEntityManagerFactoryBean amdEntityManagerFactory(EntityManagerFactoryBuilder emf){

        /*return builder
                .dataSource(aaplDataSource)
                .packages("com.Project1.demo.aapl")
                .build();
        */

        HashMap<String ,Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto","create");
        properties.put("hibernate.dialect","org.hibernate.dialect.PostgreSQL10Dialect");

        return emf.dataSource(amdDataSource()).packages("com/Project1/demo/amd").properties(properties).build() ;

        //return null;
    }
    @Bean(name = "amdTransactionManager")
    //@Primary
    //@Bean
    //public PlatformTransactionManager aaplTransactionManager(@Qualifier("aaplEntityManagerFactory") EntityManagerFactory aaplEntityManagerFactory) {
    public PlatformTransactionManager amdTransactionManager(@Qualifier("amdEntityManagerFactory") EntityManagerFactory entityManagerFactory){
        //return new JpaTransactionManager(aaplEntityManagerFactory);
        return new JpaTransactionManager(entityManagerFactory);
    }






}


