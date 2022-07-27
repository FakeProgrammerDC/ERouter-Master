//// Top-level build file where you can add configuration options common to all sub-projects/modules.
//plugins {
//    id 'com.android.application' version '7.2.1' apply false
//    id 'com.android.library' version '7.2.1' apply false
//}
//
//
//
//task clean(type: Delete) {
//    delete rootProject.buildDir
//}
// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {

    repositories {
        google()
        mavenCentral()
        maven {
            setUrl("https://jitpack.io")
        }
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.2.1")
//        classpath(kotlin("gradle-plugin", Version.ClassPathVersion.kotlinVersion))
//        classpath(kotlin("serialization", Version.ClassPathVersion.kotlinVersion))
//        classpath(Deps.ClassPath.hiltPlugin)
    }
}

task("clean", Delete::class) {
    delete = setOf(rootProject.buildDir)
}