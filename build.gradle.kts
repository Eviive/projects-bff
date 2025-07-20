plugins {
    java
    id("org.springframework.boot") version "3.5.3"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "dev.albertv.projects"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // Spring Boot
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")

    developmentOnly("org.springframework.boot:spring-boot-devtools")

	// Spring Cloud Gateway
	implementation("org.springframework.cloud:spring-cloud-starter-gateway-server-webflux")

    // Spring Addons
    implementation("com.c4-soft.springaddons:spring-addons-starter-oidc:8.1.18")

    // Lombok
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:2025.0.0")
    }
}

tasks.withType<ProcessResources> {
    filesMatching("**/application*.yml") {
        expand(project.properties)
    }
}
