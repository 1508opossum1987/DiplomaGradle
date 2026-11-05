plugins {
    java
    id("org.springframework.boot") version "4.1.2"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "org.example"
version = "0.0.1-SNAPSHOT"
description = "DiplomaGradle"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-webmvc")
    testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation ("org.postgresql:postgresql")
    implementation ("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation ("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation ("org.springframework.boot:spring-boot-starter-web")
    implementation ("org.springframework.boot:spring-boot-starter-data-jpa-test")
    implementation ("org.springframework.boot:spring-boot-starter-validation")
    implementation ("org.flywaydb:flyway-core")
    implementation ("org.flywaydb:flyway-database-postgresql")
    implementation ("org.springframework.boot:spring-boot-starter-flyway")

}
tasks.withType<Test> {
    useJUnitPlatform()
}
