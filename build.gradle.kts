import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

//import org.jetbrains.kotlin.gradle.plugin.KotlinPluginWrapper

plugins {
    kotlin("jvm") version "1.3.30"
}

group = "com.lunatech.toggl"
version = "1.0-SNAPSHOT"

repositories {
    jcenter()
    mavenCentral()
    maven(url = "https://dl.bintray.com/kittinunf/maven") {
        name = "bintray"
    }
    maven(url = "https://kotlin.bintray.com/kotlinx") {
        name = "kotlin bintray"
    }
    maven(url = "https://jitpack.io") {
        name = "jitpack"
    }
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    compile(group = "joda-time", name = "joda-time", version = "2.10.1")
//    compile("org.jetbrains.kotlinx:kotlinx-serialization-runtime:0.10.0")
    listOf("fuel", "fuel-coroutines", "fuel-gson"/*, "fuel-kotlinx-serialization"*/).forEach {
        compile(group = "com.github.kittinunf.fuel", name = it, version = "2.0.1")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}