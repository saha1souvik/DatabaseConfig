package com.souvik.StudentManagement.Config;


import com.souvik.StudentManagement.Model.StudentEntity;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@ComponentScan("com.souvik.StudentManagement")
@EnableJpaRepositories(basePackages = "com.souvik.StudentManagement.Repository", entityManagerFactoryRef = "studentEntityManagerFactory",
transactionManagerRef = "studentTransactionManager")
public class StudentDataSourceConfiguration {

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.student")
    public DataSourceProperties studentDataSourceProperty(){
        return new DataSourceProperties();
    }

    @Bean(name = "studentDataSource")
    @ConfigurationProperties("spring.datasource.student.configuration")
    public DataSource studentDataSource(){
        return studentDataSourceProperty()
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean(name = "studentEntityManagerFactory")
    @Primary
    @ConditionalOnBean(name = "studentDataSource")
    public LocalContainerEntityManagerFactoryBean studentEntityManagerFactory(EntityManagerFactoryBuilder builder
    ,@Qualifier("studentDataSource") DataSource dataSource){
        return builder.dataSource(dataSource)
                .packages(StudentEntity.class)
                .build();

    }

    @Bean(name = "studentTransactionManager")
    @Primary
    public PlatformTransactionManager studentTransactionManager(final @Qualifier("studentEntityManagerFactory") LocalContainerEntityManagerFactoryBean entityManagerFactoryBean){
       return  new JpaTransactionManager(entityManagerFactoryBean.getObject());
    }


}
