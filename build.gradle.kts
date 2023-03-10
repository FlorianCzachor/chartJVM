val d2vVersion: String by project
val chartsVersion: String by project

plugins {
    kotlin("jvm") version "1.8.0"
    application
    id("org.openjfx.javafxplugin") version "0.0.13"
}

javafx {
    version = "15.0.1"
    modules("javafx.controls", "javafx.swing")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    maven{
        url = uri("https://maven.pkg.jetbrains.space/data2viz/p/maven/dev")
    }
    maven{
        url = uri("https://maven.pkg.jetbrains.space/data2viz/p/maven/public")
    }
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation ("io.data2viz.d2v:core-jvm:$d2vVersion")
    implementation ("io.data2viz.charts:core:$chartsVersion")
    implementation ("org.openjfx:javafx-swing:17.0.6")
    implementation("org.glavo:simple-png:0.2.0")
    implementation("org.glavo:simple-png-javafx:0.2.0")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
}

application {
    mainClass.set("MainKt")
}