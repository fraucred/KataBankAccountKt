plugins {
    kotlin("jvm") version "2.0.20"
}

group = "com.fraucred"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("io.mockk:mockk:1.13.13")}

tasks.test {
    useJUnitPlatform()
}