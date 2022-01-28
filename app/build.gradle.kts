
plugins {
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.microsoft.playwright:playwright:1.17.0")
    implementation("com.approvaltests:approvaltests:13.0.0")
    implementation("org.testcontainers:testcontainers:1.16.3")


    testImplementation("org.assertj:assertj-core:3.22.0")
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
