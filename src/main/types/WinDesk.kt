package types

import javafx.stage.Window
import java.awt.Desktop

data class WinDesk(
	var window: Window? = null,
	var desktop: Desktop? = null
)