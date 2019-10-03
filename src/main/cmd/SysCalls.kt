package cmd

import javafx.beans.property.SimpleStringProperty
import types.Constants
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit

object SysCalls {

    fun updateBrew(): String? {
        return runCommand("brew update")
    }

    fun installNode(): String? {
        return runCommand("brew install node")
    }

    fun installDiff2Html(): String? {
        return runCommand("npm install -g diff2html-cli")
    }

    // TODO: exclusions, as array? or method, iOS and Android with preset exclusions
    // TODO: desktop app for git commands, pointed to directory, like rev-list --count, etc
    // TODO: rename html title and header

    fun runDiff2Html(pathToRepo: String,
                     nameOfOutFile: String,
                     commitHash1: String,
                     commitHash2: String): String? {
        val workingDir = File(pathToRepo)
        println("workingDir: $workingDir")
        return runCommand("diff2html --style side --file $nameOfOutFile.html -- -M $commitHash1 $commitHash2", workingDir = workingDir)
    }

    fun runDiff2Html(selectedExceptionType: SimpleStringProperty,
                     pathToRepo: String,
                     outDir: String,
                     nameOfOutFile: String,
                     commitHash1: String,
                     commitHash2: String): String? {
        val workingDir = File(pathToRepo)
        println("workingDir: $workingDir")
        // TODO: print statements
        val checkedOutDir = checkSlash(outDir)

        val cmdPrefix = Constants.cmdPrefix
        val excludesAndroid = Constants.excludesAndroid
        val excludesiOS = Constants.excludesiOS
        val cmdSuffix = Constants.cmdSuffix(checkedOutDir, nameOfOutFile, commitHash1, commitHash2)

        return when (selectedExceptionType.get()) {
            "Default" -> runCommand("$cmdPrefix $cmdSuffix", workingDir)
            "Android" -> runCommand("$cmdPrefix $excludesAndroid $cmdSuffix", workingDir)
            "iOS" -> runCommand("$cmdPrefix $excludesiOS $cmdSuffix", workingDir)
            else -> runCommand("$cmdPrefix $cmdSuffix", workingDir)
        }

    }

    private fun checkSlash(string: String): String {
        return if (string.last() == '/') {
            string
        } else {
            "$string/"
        }
    }

    // https://stackoverflow.com/a/52441962
    fun runCommand(string: String,
                   workingDir: File = File("."),
                   timeoutAmount: Long = 60,
                   timeoutUnit: TimeUnit = TimeUnit.SECONDS): String? {
        println("Running command: $string")
        val result = try {
            ProcessBuilder(*string.split("\\s".toRegex()).toTypedArray())
                .directory(workingDir)
                .redirectOutput(ProcessBuilder.Redirect.PIPE)
                .redirectError(ProcessBuilder.Redirect.PIPE)
                .start().apply {
                    waitFor(timeoutAmount, timeoutUnit)
                }.inputStream.bufferedReader().readText()
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
        println("Result:\n\n$result")
        return result
    }

}