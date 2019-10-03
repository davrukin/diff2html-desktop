package ui

import cmd.Actions
import cmd.FileUtils
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.scene.Parent
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import javafx.stage.StageStyle
import tornadofx.*
import types.AppFields
import types.SaveLoad
import types.Strings
import types.WinDesk
import java.awt.Desktop
import kotlin.system.exitProcess

class MainUI : View() {

	private val projDir = SimpleStringProperty()
	private val outDir = SimpleStringProperty()
	private val fileName = SimpleStringProperty()

	private val comHash1 = SimpleStringProperty()
	private val comHash2 = SimpleStringProperty()

	private val exceptionTypes = FXCollections.observableArrayList(Strings.default, Strings.android, Strings.ios)
	private val selectedExceptionType = SimpleStringProperty()

	private val consoleOutput = SimpleStringProperty()

	override val root: Parent = vbox {
		title = Strings.appTitle
		menubar {
			menu(Strings.fileMenuFile) {
				menu("Menu") {
					item("Hello").action { println("hello") }
					item("Goodbye").action { println("goodbye") }
				}
				separator()
				item(Strings.fileMenuSave, Strings.fileMenuShortcutSave).action {
					//consoleOutput.set("Saving!")
					println("Saving!")
					val sl = SaveLoad(getWinDesk(), getFields())
					Actions.saveToJson(sl)
				}
				item(Strings.fileMenuLoad, Strings.fileMenuShortcutLoad).action {
					println("Opening!")
					val sl = SaveLoad(getWinDesk(), getFields())
					Actions.loadFromJson(sl)
				}
				item(Strings.fileMenuQuit, Strings.fileMenuShortcutQuit).action {
					println("Quitting!") // TODO: prompt for save if unsaved?
					exitProcess(0) // TODO: gracefully?
				}
				// TODO: finish, modals, save, other
			}
			menu(Strings.fileMenuAbout) {
				item("About diff2html Desktop", Strings.fileMenuShortcutAbout).action {
					println("Abouting!")
					find<AboutUI>().openModal(stageStyle = StageStyle.DECORATED)
				}
			}
		}
		form {
			fieldset {
				text = Strings.diff2html
				hbox {
					field {
						text = Strings.projectDirectory
						paddingRight = 5
						textfield().bind(projDir)
					}
					button {
						text = Strings.choose
						tooltip {
							text = "Opens a folder picker to choose the input directory for the git repository"
							font = Font.font("Verdana")
						}
						action {
							val file = FileUtils.getDirectory(currentWindow)
							projDir.set(file?.absolutePath)
						}
					}
				}
				hbox {
					field {
						text = Strings.outputDirectory
						paddingRight = 5
						textfield().bind(outDir)
					}
					button {
						text = Strings.choose
						tooltip {
							text = "Opens a folder picker to choose the output directory for the html file"
							font = Font.font("Verdana")
						}
						action {
							val file = FileUtils.getDirectory(currentWindow)
							outDir.set(file?.absolutePath)
						}
					}
				}

				field {
					text = Strings.fileName
					textfield().bind(fileName)
				}

				label {
					text = Strings.customExceptions
				}

				combobox(selectedExceptionType, exceptionTypes) {
					tooltip {
						text = "Opens a dropdown list to select custom exception types for which files to " +
								"exclude in the diff report"
						font = Font.font("Verdana")
					}
				} // TODO: Custom exceptions in modal dialog?
				// TODO: option to select side-by-side or inline; default is side-by-side
				// TODO: make this live in an hbox

				field {
					text = Strings.commitHash1
					textfield().bind(comHash1)
				}
				field {
					text = Strings.commitHash2
					textfield().bind(comHash2)
				}

				hbox {
					spacing = 5.0
					button {
						text = Strings.reset
						tooltip {
							text = "Resets all fields to their default blank values"
							font = Font.font("Verdana")
						}
						action {
							Actions.reset(getFields())
						}
					}
					button {
						text = Strings.execute
						tooltip {
							text = "Executs the diff2html command"
							font = Font.font("Verdana")
						}
						action {
							val result = runAsyncWithProgress {
								Actions.execute(getFields())
							}
							consoleOutput.set(result.value)
						}
					}
					button {
						text = Strings.print
						tooltip {
							text = "Prints the values in the cells to console"
							font = Font.font("Verdana")
						}
						action {
							Actions.print(getFields())
						}
					}
					button {
						text = Strings.installTools
						tooltip {
							text = "Opens a modal where you can install the CLI tools necessary " +
									"for this to function"
							font = Font.font("Verdana")
						}
						action {
							find<ToolsUI>().openModal(stageStyle = StageStyle.DECORATED)
						}
					}
				}
				label {
					spacing = 5.0
					text = Strings.consoleOut
					style {
						fontWeight = FontWeight.BOLD
					}
				}
				textarea {
					spacing = 5.0
					isWrapText = true
					textfield().bind(consoleOutput) // TODO: this doesn't work
				}
				/*contextmenu {
					item("About").action {
						println("hello!")
					}
				}*/
				// TODO: later? ^^^
			}
		}
	}

	private fun getWinDesk(): WinDesk {
		return WinDesk(currentWindow, Desktop.getDesktop())
	}

	private fun getFields(): AppFields {
		return AppFields(projDir, outDir, fileName, comHash1, comHash2, selectedExceptionType)
	}
}


// TODO: todo text doc, issues
// TODO: file menu
// TODO: tooltips
// TODO: fonts and font sizes and font weights

class Styles : Stylesheet() {
	init {
		label {
			fontSize = 20.px
			fontWeight = FontWeight.BOLD
			backgroundColor += c("#cecece")
		}
	}
}

// TODO: add about panel with my name
// TODO: build and testflight, etc
// TODO: put this on github