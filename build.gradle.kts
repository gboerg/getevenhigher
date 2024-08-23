plugins {
    kotlin("jvm") version "2.0.0"
    id("io.papermc.paperweight.userdev") version "1.7.2"
}

group = "me.bycoba"
version = "0.2-ALPHA"

repositories {
    mavenCentral()
}

dependencies {
    paperweight.paperDevBundle("1.21-R0.1-SNAPSHOT")
    implementation("de.miraculixx:kpaper:1.2.0")
}

tasks {
    build {
        dependsOn(reobfJar)
    }

    withType<ProcessResources> {
        expand(
            "version" to project.version,
            "name" to project.name,
        )
    }
}

kotlin {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21)
        apiVersion.set(org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_2_0)
        freeCompilerArgs.addAll(
            listOf(
                "-opt-in=kotlin.RequiresOptIn"
            )
        )
    }
    jvmToolchain(21)
}