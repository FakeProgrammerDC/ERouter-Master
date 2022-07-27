
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
    }
}

task("clean", Delete::class) {
    delete = setOf(rootProject.buildDir)
}