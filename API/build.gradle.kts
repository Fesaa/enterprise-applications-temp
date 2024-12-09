import org.springframework.core.annotation.AnnotationFilter.packages

plugins {
    id("idea")
    id("java")
    id("org.springframework.boot") version "3.3.4"
    id("io.spring.dependency-management") version "1.1.6"
    id("io.ebean") version "15.6.0"
}

group = "art.ameliah.ehb"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(23)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-security")

    implementation("com.auth0:java-jwt:4.4.0")

    implementation("io.ebean:ebean:15.6.0")
    implementation("io.ebean:ebean-querybean:15.6.0")
    implementation("io.ebean:ebean-agent:15.6.0")
    implementation("jakarta.annotation:jakarta.annotation-api:3.0.0")

    implementation("org.flywaydb:flyway-core")
    implementation("org.flywaydb:flyway-database-postgresql")

    implementation("org.modelmapper:modelmapper:3.2.1")

    compileOnly("org.projectlombok:lombok")

    runtimeOnly("org.postgresql:postgresql")

    annotationProcessor("org.projectlombok:lombok")
    annotationProcessor("io.ebean:querybean-generator:15.6.0")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

ebean {
    packages("art.ameliah.ehb.anki.api.models")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
