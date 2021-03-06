import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.31"
    id("maven-publish")
    id("com.github.johnrengelman.shadow") version "6.1.0"
}

buildscript {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.dokka:dokka-gradle-plugin:1.5.0")
        classpath("com.github.jengelman.gradle.plugins:shadow:6.1.0")
    }
}

group = "net.neverstopgaming"
version = "1.0"

repositories {
    mavenCentral()
    for (field in Repositories::class.java.declaredFields) {
        if (field.name != "INSTANCE") {
            println("Added Repository: " + field.get(null))
            maven(field.get(null))
        }
    }
    maven {
        url = uri("https://repo.neverstopgaming.net/repository/maven-internal/")
        credentials {
            this.username = System.getenv("repoName") ?: System.getenv("NEXUS_USER") ?: System.getProperty("publishName")
            this.password = System.getenv("repoPassword") ?: System.getenv("NEXUS_PASSWORD") ?: System.getProperty("publishPassword")
        }
    }
}

dependencies {
    testImplementation(kotlin("test"))
    compileOnly(getDependency("simplecloud", "plugin"))
    compileOnly(getDependency("google", "gson"))
    compileOnly(getDependency("simplecloud", "launcher"))
    compileOnly(getDependency("components", "minimessage"))
    implementation(getDependency("database", "influxdb"))
}

tasks.test {
    useJUnit()
}

tasks {

    shadowJar {

        archiveFileName.set("${project.name}-${Properties.version}-full.jar")
        exclude("META-INF/**")

        doFirst {
            //Set Manifest
            manifest {
                attributes["Implementation-Title"] = project.name
                attributes["Implementation-Version"] = Properties.version
                attributes["Specification-Version"] = Properties.version
                attributes["Implementation-Vendor"] = "NeverStopGaming.net"
                attributes["Built-By"] = System.getProperty("user.name")
                attributes["Build-Jdk"] = System.getProperty("java.version")
                attributes["Created-By"] = "Gradle ${gradle.gradleVersion}"
                attributes["Commit-Hash"] = getCommitHash()
                attributes["Launcher-Agent-Class"] = "eu.vironlab.vextension.dependency.DependencyAgent"
            }
        }
    }
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}

if (System.getProperty("publishName") != null && System.getProperty("publishPassword") != null) {
    publishing {
        (components["java"] as AdhocComponentWithVariants).withVariantsFromConfiguration(configurations["shadowRuntimeElements"]) {
            skip()
        }
        publications {
            create<MavenPublication>(project.name) {
                groupId = Properties.group
                artifactId = project.name
                version = Properties.version
                from(components["java"])
                pom {
                    name.set(project.name)
                    url.set("https://github.com/NeverStopGaming/Backend")
                    properties.put("inceptionYear", "2021")
                    licenses {
                        license {
                            name.set("All Rights Reserved")
                            url.set("All Rights Reserved")
                            distribution.set("repo")
                        }
                    }
                    developers {
                        developer {
                            id.set("Chaoten")
                            name.set("Ben Chaoten")
                            email.set("chaoten@NeverStopGaming.net")
                        }
                    }
                }
            }
            repositories {
                maven("https://repo.NeverStopGaming.net/repository/maven-internal/") {
                    this.name = "maven-internal"
                    credentials {
                        this.password = System.getProperty("publishPassword")
                        this.username = System.getProperty("publishName")
                    }
                }
            }
        }
    }
}