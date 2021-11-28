
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
	id("org.springframework.boot") version "2.5.6"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.5.31"
	kotlin("kapt") version "1.5.0"
	kotlin("plugin.spring") version "1.5.31"
	kotlin("plugin.jpa") version "1.5.0"
}

repositories {
	jcenter()
	mavenCentral()
}

apply(plugin = "kotlin")
apply(plugin = "kotlin-kapt")
apply(plugin = "org.springframework.boot")
apply(plugin = "io.spring.dependency-management")
apply(plugin = "org.jetbrains.kotlin.plugin.spring")
apply(plugin = "org.jetbrains.kotlin.plugin.jpa")

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
	runtimeOnly("mysql:mysql-connector-java")

	// map-struct
	implementation("org.mapstruct:mapstruct:1.4.0.Final")
	kapt("org.mapstruct:mapstruct-processor:1.4.0.Final")

	//implementation("org.springframework.cloud:spring-cloud-starter-aws:2.2.5.RELEASE")

    // jpa
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")

	implementation("org.springframework.boot:spring-boot-starter-data-rest")

	// swagger
	implementation("org.springdoc:springdoc-openapi-ui:1.5.3")

	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

	// JPA 엔티티 히스토리
	implementation("org.springframework.data:spring-data-envers")

	// spring security
	implementation("org.springframework.boot:spring-boot-starter-security:2.5.6")
	// jwt
	implementation("com.auth0:java-jwt:3.18.2")
	implementation("io.jsonwebtoken:jjwt-impl:0.11.2")
	runtimeOnly("io.jsonwebtoken:jjwt-api:0.11.2")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.2")

	// devtool
	developmentOnly("org.springframework.boot:spring-boot-devtools")

	// 테스트
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.boot:spring-boot-starter-jdbc")
	developmentOnly("com.h2database:h2")
	testImplementation("com.h2database:h2")
	testImplementation("org.junit.jupiter:junit-jupiter-engine")
	testImplementation("org.jetbrains.kotlin:kotlin-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit")

}

tasks {
	compileKotlin {
		kotlinOptions {
			freeCompilerArgs = listOf("-Xjsr305=strict")
			jvmTarget = "15"
		}
		dependsOn(processResources) // kotlin 에서 ConfigurationProperties
	}


	compileTestKotlin {
		kotlinOptions {
			freeCompilerArgs = listOf("-Xjsr305=strict")
			jvmTarget = "15"
		}
	}
}

val jar: Jar by tasks
val bootJar: BootJar by tasks

bootJar.enabled = false
jar.enabled = false