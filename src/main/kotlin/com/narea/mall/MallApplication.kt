package com.narea.mall

import com.narea.mall.auth.AUTHORIZATION_HEADER
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.security.SecurityScheme
import io.swagger.v3.oas.annotations.servers.Server
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@OpenAPIDefinition(
	servers = [Server(url = "/")]
)
@SecurityScheme(
	name = AUTHORIZATION_HEADER,
	type = SecuritySchemeType.APIKEY,
	`in` = SecuritySchemeIn.HEADER
)
@SpringBootApplication
class MallApplication

fun main(args: Array<String>) {
	runApplication<MallApplication>(*args)
}
