package dev.jacob.a2_draft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class A2DraftApplication {

	public static void main(String[] args) {
		SpringApplication.run(A2DraftApplication.class, args);
	}

}
