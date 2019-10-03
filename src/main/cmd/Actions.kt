package cmd

import com.beust.klaxon.Klaxon
import types.*

object Actions {

    fun reset(fields: AppFields) {
        with(fields) {
            projDir.set("")
            outDir.set("")
            fileName.set("")
            comHash1.set("")
            comHash2.set("")
            selectedExceptionType.set(Strings.default)
        }
    }

    fun execute(fields: AppFields): String {
        with(fields) {
            val pathToRepo = projDir.get()
            val nameOfOutDir = outDir.get()
            val nameOfOutFile = fileName.get()
            val commitHash1 = comHash1.get()
            val commitHash2 = comHash2.get()
            return SysCalls.runDiff2Html(selectedExceptionType, pathToRepo, nameOfOutDir, nameOfOutFile, commitHash1, commitHash2) ?:
            "Execution failed, please make sure you have the tools installed"
        }
    }

    fun print(fields: AppFields) {
        with(fields) {
            val pathToRepo = projDir.get()
            val nameOfOutDir = outDir.get()
            val nameOfOutFile = fileName.get()
            val commitHash1 = comHash1.get()
            val commitHash2 = comHash2.get()
            val selectedException = selectedExceptionType.get()
            println("Printing field values:\n" +
                    "pathToRepo: $pathToRepo\n" +
                    "nameOfOutDir: $nameOfOutDir\n" +
                    "nameOfOutFile: $nameOfOutFile\n" +
                    "commitHash1: $commitHash1\n" +
                    "commitHash2: $commitHash2\n" +
                    "selectedException: $selectedException")
        }
    }

    // TODO: auto-generate filename or allow user to pick one?
    fun saveToJson(sl: SaveLoad) {
        val file = FileUtils.saveFile(sl.winDesk.window)
        val fields = convertPropertiesToValues(sl.appFields)
        val json = Klaxon().toJsonString(fields)
        file?.writeText(json)
    }

    fun loadFromJson(sl: SaveLoad) {
        val file = FileUtils.getFile(sl.winDesk.window)
        val fields = Klaxon().parse<Fields>(file?.readText() ?: "")
        convertValuesToProperties(fields, sl.appFields)
    }
}