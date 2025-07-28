import org.springframework.boot.gradle.tasks.aot.ProcessAot
import org.springframework.boot.gradle.tasks.bundling.BootBuildImage

plugins {
    id("java")
    id("idea")
    id("org.springframework.boot") version "3.5.3"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.graalvm.buildtools.native") version "0.11.0"
}

group = "dev.albertv.projects"
version = "1.0.0"

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

tasks.withType<BootBuildImage> {
    environment.put("BP_NATIVE_IMAGE_BUILD_ARGUMENTS", "--initialize-at-build-time=org.slf4j.helpers.Reporter")
}

graalvmNative {
    binaries {
        named("main") {
            buildArgs.add("--initialize-at-build-time=org.slf4j.helpers.Reporter")
        }
    }
}

tasks.withType<ProcessAot> {
    args("--spring.profiles.active=prd")
}
