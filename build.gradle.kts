plugins {
    kotlin("jvm") version "2.0.20"
    application
}

application {
    // Specifica il nome della classe principale (deve includere il pacchetto completo)
    mainClass.set("me.mintdev.MainKt")
}

group = "me.mintdev"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("com.github.ajalt.clikt:clikt:5.0.0")
    implementation("com.github.ajalt.clikt:clikt-markdown:5.0.0")
    implementation("com.ashampoo:kim:0.18.4")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}
