package ui

import javafx.scene.text.FontWeight
import tornadofx.*

class AboutUI : Fragment() {

	override val root = vbox {
		title = "About diff2html Desktop"
		paddingAll = 20
		hbox {
			spacing = 15.0
			label {
				text = "Author: "
			}
			label {
				text = "Daniel Avrukin"
			}
			label {
				text = "© 2019, All Rights Reserved"
			}
		}
		hbox {
			spacing = 15.0
			text() {
				text = """
					Built with TornadoFX in IntelliJ.
					Libraries used: tornadofx 1.7.17, kotlin 1.3.50, and klaxon 5.0.13.
				""".trimIndent()
			}
		}
	}
}
