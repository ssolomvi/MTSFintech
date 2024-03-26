plugins {
    id("java")
    id("org.springframework.boot") version("2.4.5")
    id("io.spring.dependency-management") version("1.1.4")

    application
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "mts.animals.app.AnimalStudyApp"
    }
}

application {
    mainClass = "mts.animals.app.AnimalStudyApp"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("net.bytebuddy:byte-buddy:1.14.11")
    implementation("javax.annotation:javax.annotation-api:1.3.2")
    implementation("org.springframework.boot:spring-boot-starter:2.4.5")
    implementation(project(":animal-configuration-starter"))

    testImplementation(platform("org.junit:junit-bom:5.9.2"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.mockito:mockito-core:5.3.1")
    testImplementation("org.mockito:mockito-junit-jupiter:5.3.1")
    testImplementation("org.springframework.boot:spring-boot-starter-test:2.4.5")
}

tasks.test {
    useJUnitPlatform()
}

task("prepareKotlinBuildScriptModel") {

}