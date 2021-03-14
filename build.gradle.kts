plugins {
    java
    kotlin("jvm") version "1.4.31"
    kotlin("kapt") version "1.4.31"
}

group = "com.lehaine"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven(url = "https://jitpack.io")
}


configurations.all {
    if (name.contains("kapt")) {
        attributes.attribute(
            org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType.attribute,
            org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType.jvm
        )
        attributes.attribute(Usage.USAGE_ATTRIBUTE, objects.named(Usage::class.java, Usage.JAVA_RUNTIME))
    }
}

val ktLdtkApiVersion = "master-SNAPSHOT"
dependencies {
    implementation("com.badlogicgames.gdx:gdx-backend-lwjgl3:1.9.12")
    implementation("com.badlogicgames.gdx:gdx-platform:1.9.12:natives-desktop")
    implementation("com.badlogicgames.gdx:gdx:1.9.12")
    implementation("com.lehaine.kt-ldtk-api:libgdx-backend:$ktLdtkApiVersion")
    implementation("com.lehaine.kt-ldtk-api:ldtk-api:$ktLdtkApiVersion")
    kapt("com.lehaine.kt-ldtk-api:libgdx-ldtk-processor:$ktLdtkApiVersion")
}


val jar by tasks.getting(Jar::class) {
    manifest {
        attributes["Main-Class"] = "com.lehaine.ldtktest.LDtkTest"
    }
}