package com.example.is_backend.config;


import io.github.cdimascio.dotenv.Dotenv;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(basePackages = "com.example.is_backend.repository")
public class JpaConfig {


    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        Dotenv dotenv = Dotenv.load();
        String dbURL = dotenv.get("DB_URL");
        String dbUsername = dotenv.get("DB_USERNAME");
        String dbPassword = dotenv.get("DB_PASSWORD");

        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setPersistenceProviderClass(org.eclipse.persistence.jpa.PersistenceProvider.class);
        em.setPersistenceUnitName("default");
        em.setPackagesToScan("com.example.is_backend.entity");

        Map<String, Object> properties = new HashMap<>();
        properties.put("jakarta.persistence.jdbc.url", dbURL);
        properties.put("jakarta.persistence.jdbc.user", dbUsername);
        properties.put("jakarta.persistence.jdbc.password", dbPassword);
        properties.put("jakarta.persistence.jdbc.driver", "org.postgresql.Driver");


        properties.put("eclipselink.logging.level", "ALL");
        properties.put("eclipselink.ddl-generation", "create-or-extend-tables");
        properties.put("eclipselink.weaving", "false");
        em.setJpaPropertyMap(properties);
        return em;
    }


    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

}
