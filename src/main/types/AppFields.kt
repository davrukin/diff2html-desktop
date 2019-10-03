package types

import javafx.beans.property.SimpleStringProperty

data class AppFields(
	val projDir: SimpleStringProperty,
	val outDir: SimpleStringProperty,
	val fileName: SimpleStringProperty,
	val comHash1: SimpleStringProperty,
	val comHash2: SimpleStringProperty,
	val selectedExceptionType: SimpleStringProperty
)

data class Fields(
	val projDir: String,
	val outDir: String,
	val fileName: String,
	val comHash1: String,
	val comHash2: String,
	val selectedExceptionType: String
)

fun convertValuesToProperties(fields: Fields?, appFields: AppFields) {
	fields?.let {
		appFields.projDir.set(it.projDir)
		appFields.outDir.set(it.outDir)
		appFields.fileName.set(it.fileName)
		appFields.comHash1.set(it.comHash1)
		appFields.comHash2.set(it.comHash2)
		appFields.selectedExceptionType.set(it.selectedExceptionType)
	}
}

fun convertPropertiesToValues(appFields: AppFields): Fields {
	return Fields(
		appFields.projDir.get(), appFields.outDir.get(),
		appFields.fileName.get(), appFields.comHash1.get(),
		appFields.comHash2.get(), appFields.selectedExceptionType.get()
	)
}