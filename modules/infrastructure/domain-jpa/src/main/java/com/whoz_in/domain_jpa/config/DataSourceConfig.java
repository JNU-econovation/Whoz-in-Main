package com.whoz_in.domain_jpa.config;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import java.util.Map;
import javax.sql.DataSource;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.whoz_in.domain_jpa",
        entityManagerFactoryRef = "domainJpaEntityManagerFactory",
        transactionManagerRef = "domainJpaTM"
)
@RequiredArgsConstructor
public class DataSourceConfig {
    //DB 연결 세팅값 객체
    //잠깐쓸거라 밖으로 빼지 않았음
    //설정마다 필드가 다를 수 있으므로 공통 모듈로 빼지 않았음
    @Getter
    @Setter
    public static class DataSourceProperties {
        private String url;
        private String username;
        private String password;
        private String driverClassName;
    }
    //하이버네이트 관련 설정값 객체
    @Getter
    @Setter
    public static class HibernateProperties {
        private String ddlAuto;
        private boolean formatSql;
        private boolean showSql;
    }

    //세팅값 객체를 빈으로 등록함
    //@Bean 메서드를 통해 자동으로 빈 등록이 되기 때문에 @ConfigurationPropertiesScan 없이도 동작함
    @Bean
    @ConfigurationProperties("domain-jpa.datasource")
    public DataSourceProperties domainJpaDataSourceProperties(){
        return new DataSourceProperties();
    }
    @Bean
    @ConfigurationProperties("domain-jpa.hibernate")
    public HibernateProperties domainJpaHibernateProperties(){
        return new HibernateProperties();
    }

    //세팅값을 통해 DataSource 생성
    @Primary
    @Bean
    public DataSource domainJpaDataSource(DataSourceProperties properties) {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setPoolName("DomainJpaHikariPool");
        dataSource.setJdbcUrl(properties.getUrl());
        dataSource.setUsername(properties.getUsername());
        dataSource.setPassword(properties.getPassword());
        dataSource.setDriverClassName(properties.getDriverClassName());
        return dataSource;
    }

    //JPA 설정
    @Bean
    public LocalContainerEntityManagerFactoryBean domainJpaEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("domainJpaDataSource") DataSource dataSource,
            @Qualifier("domainJpaHibernateProperties") HibernateProperties hibernateProperties) {
        return builder
                .dataSource(dataSource) //DataSource 지정
                .persistenceUnit("domain_jpa") //이름
                .packages("com.whoz_in.domain_jpa") //JPA 엔티티가 존재하는 패키지
                .properties(
                        Map.of(
                            "hibernate.hbm2ddl.auto", hibernateProperties.ddlAuto,
                            "hibernate.show_sql", hibernateProperties.showSql,
                            "hibernate.format_sql", hibernateProperties.formatSql
                        )
                )
                .build();
    }

    //TransactionManager가 여러 개인 경우 트랜잭션에서 알맞게 골라 사용해야 함
    //@Primary를 붙이면 t.m을 설정하지 않아도 알아서 골라버리므로 잘못된 t.m을 사용할 가능성이 있음
    //따라서 @Primary를 설정하지 않음으로써 t.m이 2개 이상 등록된 경우에 명시적으로 설정하도록 강제한다.
    @Bean("domainJpaTM")
    public PlatformTransactionManager domainJpaTransactionManager(
            @Qualifier("domainJpaEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        //필요하면 설정 추가하기
        return new JpaTransactionManager(entityManagerFactory);
    }
}