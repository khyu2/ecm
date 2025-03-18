package project.ecm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class EcmApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcmApplication.class, args);
	}

}