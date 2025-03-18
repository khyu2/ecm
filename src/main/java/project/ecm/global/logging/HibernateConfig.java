package project.ecm.global.logging;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class HibernateConfig {

    private final ApiQueryInspector apiQueryInspector;

     @Bean
     public HibernatePropertiesCustomizer hibernatePropertiesCustomizer() {
         return hibernateProperties -> hibernateProperties
                 .put("hibernate.session_factory.statement_inspector", apiQueryInspector);
     }
}
