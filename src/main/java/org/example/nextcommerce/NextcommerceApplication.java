package org.example.nextcommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class NextcommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NextcommerceApplication.class, args);
	}

}
