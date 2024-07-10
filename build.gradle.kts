plugins {
    java
    application
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "oop.sunfun"
version = "1.0"

repositories {
    mavenCentral()
}

val supportedPlatforms = listOf("linux", "mac", "win")

dependencies {
    val jUnitVersion = "5.10.3"
    testImplementation("org.junit.jupiter:junit-jupiter-api:$jUnitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$jUnitVersion")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

val main: String by project

application {
    mainClass.set(main)
}