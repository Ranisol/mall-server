package com.spring.security

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.servers.Server
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@OpenAPIDefinition(
	//servers = [Server(url = "/")]
)
@SpringBootApplication
class SecurityApplication

fun main(args: Array<String>) {

	runApplication<SecurityApplication>(*args)
}
