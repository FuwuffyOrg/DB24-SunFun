plugins {
    java
    application
    checkstyle
    pmd
    id("com.github.spotbugs") version "6.0.18"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

checkstyle {
    toolVersion = "10.17.0"
    configFile = file("${rootDir}/config/checkstyle/checkstyle.xml")
}

pmd {
    toolVersion = "7.3.0"
    // ruleSets = listOf("category/java/bestpractices.xml", "category/java/design.xml")
}

spotbugs {
    toolVersion = "6.0.18"
}

group = "oop.sunfun"
version = "1.0"

repositories {
    mavenCentral()
}

val supportedPlatforms = listOf("linux", "mac", "win")

dependencies {
    compileOnly("com.github.spotbugs:spotbugs-annotations:4.8.3")

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