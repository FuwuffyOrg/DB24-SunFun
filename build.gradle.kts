plugins {
    java
    application
    checkstyle
    pmd
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

checkstyle {
    toolVersion = "10.17.0"
    configFile = file("${rootDir}/config/checkstyle/checkstyle.xml")
}

pmd {
    toolVersion = "7.3.0"
    ruleSets = listOf("category/java/bestpractices.xml", "category/java/design.xml")
}

group = "oop.sunfun"
version = "1.0"

repositories {
    mavenCentral()
}

val supportedPlatforms = listOf("linux", "mac", "win")

dependencies {
    // Connection to sql
    implementation("com.mysql:mysql-connector-j:9.0.0")

    // Stuff for better UI
    implementation("com.formdev:flatlaf:3.4.1")
    implementation("com.formdev:flatlaf-extras:3.4.1")
    implementation("com.formdev:flatlaf-intellij-themes:3.4.1")
}

val main: String by project

application {
    mainClass.set(main)
}