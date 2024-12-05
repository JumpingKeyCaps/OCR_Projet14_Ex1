package com.kirabium.relayance.test

import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import org.junit.BeforeClass
import org.junit.runner.RunWith
import java.io.File

/**
 * Runner pour exécuter les tests Cucumber dans l'environnement Android.
 */
@RunWith(Cucumber::class)
@CucumberOptions(
    features = ["classpath:features"],
    glue = ["com.kirabium.relayance.stepdefs"],
    plugin = [
        "pretty",
        "html:/data/data/com.kirabium.relayance/files/reports/cucumber-report.html"
    ],
    tags = "@test",
    dryRun = false
)
class CucumberTestRunner {
    companion object {
        @JvmStatic
        @BeforeClass
        fun setupDirectories() {
            val reportDir = File("/data/data/com.kirabium.relayance/files/reports")
            if (!reportDir.exists()) {
                reportDir.mkdirs() // Crée le répertoire s'il n'existe pas
            }


            // Debug pour vérifier la détection des fichiers
            val featuresDir = File("src/androidTest/assets/features")
            println("Features Directory Exists: ${featuresDir.exists()}")
            println("Files in Features Directory: ${featuresDir.listFiles()?.joinToString(",")}")



        }
    }
}