plugins {
    id("java")
    id("org.springframework.boot") version("2.4.5")
    id("io.spring.dependency-management") version("1.1.4")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.2"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.mockito:mockito-core:5.3.1")
    testImplementation("org.mockito:mockito-junit-jupiter:5.3.1")
    // https://mavenlibs.com/maven/dependency/org.springframework/spring-context
    implementation("org.springframework:spring-context:5.3.29")
    // https://mvnrepository.com/artifact/javax.annotation/javax.annotation-api
    implementation("javax.annotation:javax.annotation-api:1.3.2")
    implementation("org.springframework.boot:spring-boot-starter")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation(project(":animal-configuration-starter"))
}

tasks.test {
    useJUnitPlatform()
}