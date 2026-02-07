package com.bank.prestamos;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
	info = @Info(
		title = "API de Gestión de Préstamos",
		version = "1.0.0",
		description = "API REST para gestionar solicitudes de préstamos personales con arquitectura hexagonal",
		contact = @Contact(
			name = "Equipo de Desarrollo",
			email = "gdlcruzguzman@gmail.com"
		)
	)
)
public class BankApiRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankApiRestApplication.class, args);
	}

}
