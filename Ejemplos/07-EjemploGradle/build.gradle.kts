// Comportamiento para el proyecto
plugins {
    kotlin("jvm") version "1.9.0"
    application
    // Plugin de Dokka para la documentación
    id("org.jetbrains.dokka") version "1.9.0"
}

group = "dev.joseluisgs"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // Mordant!!
    // Nos sirve para instalar la librerías para usarlas
    // Si quieres usar la última versión, debes usar estas dos
    implementation("com.github.ajalt.mordant:mordant:2.2.0")
    implementation("net.java.dev.jna:jna:5.13.0")

    // Si quieres usar la beta 9, no neceitas nada mas que esta
    // implementation("com.github.ajalt.mordant:mordant-jvm:2.0.0-beta9")

    testImplementation(kotlin("test"))
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

// tarea para el fat jar
tasks.jar {
    manifest {
        // definimos el punto de entrada del manifest
        // Es la clase que contiene el método main que queremos ejecutar
        // Recuerda que no se pone el punto del fichero, si no en mayusuculas
        // Tampoco se pone .class
        attributes["Main-Class"] = "MainKt"
    }
    // Añadimos las dependencias de compilación al jar
    configurations["compileClasspath"].forEach { file: File ->
        from(zipTree(file.absoluteFile))
    }
    // configuramos la estrategia de duplicados
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}