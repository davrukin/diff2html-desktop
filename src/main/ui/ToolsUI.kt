package ui

import cmd.SysCalls
import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Insets
import javafx.scene.Parent
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import tornadofx.*
import types.Strings

class ToolsUI : Fragment() {
	private val consoleOutput = SimpleStringProperty()

	override val root: Parent = vbox {
		title = Strings.installTools
		paddingAll = 20
		button {
			padding = Insets(5.0, 10.0, 5.0, 10.0)
			spacing = 15.0
			text = Strings.updateBrew
			tooltip {
				text = "Updates homebrew (Mac-specific)"
				font = Font.font("Verdana")
			}
			action {
				val result = runAsyncWithProgress {
					SysCalls.updateBrew() ?: "Update brew failed, please install brew"
				}
				consoleOutput.set(result.value)
			}
		}
		button {
			padding = Insets(5.0, 10.0, 5.0, 10.0)
			spacing = 15.0
			text = Strings.installNpm
			tooltip {
				text = "Installs npm through brew (Mac-specific)"
				font = Font.font("Verdana")
			}
			action {
				val result = runAsyncWithProgress {
					SysCalls.installNode() ?: "Install NPM failed, please try again or in Terminal"
				}
				consoleOutput.set(result.value)
			}
		}
		button {
			padding = Insets(5.0, 10.0, 5.0, 10.0)
			spacing = 15.0
			text = Strings.installCli
			tooltip {
				text = "Installs the diff2html cli globally through npm"
				font = Font.font("Verdana")
			}
			action {
				val result = runAsyncWithProgress {
					SysCalls.installDiff2Html()
							?: "Install diff2html failed, please try again or in Terminal"
				}
				consoleOutput.set(result.value)
			}
		}
		// TODO: mac/windows/linux options for commands
		label {
			text = Strings.consoleOut
			style {
				fontWeight = FontWeight.BOLD
			}
		}
		textarea {
			isWrapText = true
			textfield().bind(consoleOutput)
		}
	}
}