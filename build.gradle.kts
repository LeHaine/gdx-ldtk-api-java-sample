import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    kotlin("jvm") version "1.4.20"
    kotlin("kapt") version "1.4.20"
    id("com.github.johnrengelman.shadow") version "5.2.0"
}

group = "com.lehaine"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven(url = "https://jitpack.io")
}

dependencies {
    implementation("com.badlogicgames.gdx:gdx-backend-lwjgl3:1.9.12")
    implementation("com.badlogicgames.gdx:gdx-platform:1.9.12:natives-desktop")
    implementation("com.badlogicgames.gdx:gdx:1.9.12")
    implementation("com.lehaine.gdx-ldtk-api:libgdx-backend:0.6.1")
    implementation("com.lehaine.gdx-ldtk-api:ldtk-api:0.6.1")
    kapt("com.lehaine.gdx-ldtk-api:libgdx-ldtk-processor:0.6.1")
    testCompile("junit", "junit", "4.12")
}


val jar by tasks.getting(Jar::class) {
    manifest {
        attributes["Main-Class"] = "com.lehaine.ldtktest.LDtkTest"
    }
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }
    withType<ShadowJar> {
        archiveClassifier.set("")
    }
}