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
}

dependencies {
    implementation(files("libs/ldtk-api-1.0-SNAPSHOT.jar"))
    kapt(files("libs/ldtk-processor-1.0-SNAPSHOT.jar"))
    testCompile("junit", "junit", "4.12")
}


val jar by tasks.getting(Jar::class) {
    manifest {
        attributes["Main-Class"] = "com.lehaine.ldtktest.LDtkTest"
    }
}

tasks {
    shadowJar {
        archiveClassifier.set("")
    }
}