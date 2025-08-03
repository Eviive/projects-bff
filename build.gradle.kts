import org.springframework.boot.gradle.tasks.aot.ProcessAot

plugins {
    id("java")
    id("idea")
    id("checkstyle")
    id("org.springframework.boot") version "3.5.3"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.graalvm.buildtools.native") version "0.11.0"
}

group = "dev.albertv.projects"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

checkstyle {
    toolVersion = "10.26.1"
    configFile = file("config/checkstyle/config.xml")
}

repositories {
    mavenCentral()
}

dependencies {
    // Spring Boot
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    implementation("org.springframework.boot:spring-boot-starter-actuator")

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

graalvmNative {
    binaries {
        named("main") {
            imageName = "bff"
            buildArgs.add("--initialize-at-build-time=org.slf4j.helpers.Reporter")
            buildArgs.add("-march=compatibility")
        }
    }
}

tasks.withType<ProcessAot> {
    args("--spring.profiles.active=prd")
}
