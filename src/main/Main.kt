import cmd.SysCalls
import javafx.application.Application
import tornadofx.*
import ui.MainUI

object Main {

    @JvmStatic
    fun main(args: Array<String>) {
        val result = SysCalls.runCommand("pwd")
        println("pwd: $result")

        //println(SysCalls.updateBrew())
        //println(SysCalls.installNode())
        //println(SysCalls.installDiff2Html())

        //println(SysCalls.runDiff2Html(pathToRepo, outfile, commit1, commit2))

        Application.launch(DiffApp::class.java)
    }

    private fun doUiStuff() {


        DiffApp().init()

    }

}

class DiffApp : App(MainUI::class)