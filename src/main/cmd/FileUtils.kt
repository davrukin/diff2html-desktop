package cmd

import javafx.stage.DirectoryChooser
import javafx.stage.FileChooser
import javafx.stage.Window
import types.WinDesk
import java.awt.Desktop
import java.io.File

object FileUtils {

    // open file chooser, return file path of selected directory

    // https://docs.oracle.com/javafx/2/ui_controls/file-chooser.htm

    fun getDirectory(window: Window?): File? {
        val dirChooser = DirectoryChooser()
        configureDirectoryChooser(dirChooser)
        return dirChooser.showDialog(window)
    }

    fun getFile(window: Window?): File? {
        val fileChooser = FileChooser()
        configureFileChooser(fileChooser)
        return fileChooser.showOpenDialog(window)
    }

    fun saveFile(window: Window?): File? {
        val fileChooser = FileChooser()
        configureFileChooser(fileChooser)
        return fileChooser.showSaveDialog(window)
    }

    fun getFiles(window: Window?): List<File?>? {
        val fileChooser = FileChooser()
        configureFileChooser(fileChooser)
        return fileChooser.showOpenMultipleDialog(window)
    }

    //////////////////////////////////////////////////////////

    fun openDirectory(winDesk: WinDesk) {
        val dir = getDirectory(winDesk.window)
        open(dir, winDesk.desktop)
    }

    fun openFile(winDesk: WinDesk) {
        val file = getFile(winDesk.window)
        open(file, winDesk.desktop)
    }

    fun openFiles(winDesk: WinDesk) {
        val files = getFiles(winDesk.window)
        files?.forEach {
            open(it, winDesk.desktop)
        }
    }

    //////////////////////////////////////////////////////////

    private fun configureDirectoryChooser(chooser: DirectoryChooser) {
        chooser.initialDirectory = homeDirectory
    }

    private fun configureFileChooser(chooser: FileChooser) {
        with(chooser) {
            initialDirectory = homeDirectory
            extensionFilters.addAll(
                FileChooser.ExtensionFilter("json", "*.json")
            )
        }
    }

    //////////////////////////////////////////////////////////

    private val homeDirectory = File(System.getProperty("user.home"))

    private fun open(file: File?, desktop: Desktop?) {
        file?.let {
            try {
                desktop?.open(it)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

// TODO: javadoc
