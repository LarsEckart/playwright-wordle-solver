
plugins {
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.microsoft.playwright:playwright:1.17.0")
    testImplementation("org.assertj:assertj-core:3.22.0")
    testImplementation("com.approvaltests:approvaltests:13.0.0")
    testImplementation("com.larseckart:junit-tcr-extensions:0.0.1")
}

testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            useJUnitJupiter("5.8.2")
        }
    }
}

application {
    mainClass.set("com.larseckart.wordle.App")
}
