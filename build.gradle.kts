val kotestVersion = "5.5.4"

plugins {
    kotlin("jvm") version "1.7.22"
    id("io.kotest") version "0.3.8"
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("io.kotest:kotest-assertions-core-jvm:$kotestVersion")
    testImplementation("io.kotest:kotest-framework-engine-jvm:$kotestVersion")
    testImplementation("io.kotest:kotest-framework-datatest-jvm:$kotestVersion")
}

tasks {
    sourceSets {
        main {
            java.srcDirs("src")
        }
    }

    wrapper {
        gradleVersion = "7.6"
    }
}
