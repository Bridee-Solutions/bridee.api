package com.bridee.api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.web.config.EnableSpringDataWebSupport;


import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@EnableFeignClients
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
@OpenAPIDefinition(
		info = @Info(
				title = "Bridee Application",
				version = "1.0",
				description = "Documentação da primeira versão da API da Bridee",
				contact = @Contact(
						name = "bridee solutions",
						email = "brideesolutions@gmail.com")
		)
)
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
    }

}
