package io.github.s8a.koditlin


import javafx.scene.control.TextArea
import javafx.scene.text.Font
import tornadofx.*


class KoditlinApp : App(KoditlinView::class)

class KoditlinView : View("Koditlin") {
    var textArea: TextArea by singleAssign()

    private var currentFile = ""
    private var saved = false

    override val root = borderpane {
        top = menubar {
            menu("File") {
                item("New", "Ctrl+N") {
                    action { newFile() }
                }
                separator()
                item("Open", "Ctrl+O") {
                    action { openFile() }
                }
                separator()
                item("Save", "Ctrl+S") {
                    action { saveFile() }
                }
            }
            menu("Edit") {
                item("Undo", "Ctrl+Z") {
                    action { textArea.undo() }
                }
                item("Redo", "Ctrl+Shift+Z") {
                    action { textArea.redo() }
                }
            }
        }
        center = scrollpane {
            textarea() {
                prefRowCount = 24
                prefColumnCount = 80
                font = Font.font("monospace")
                textArea = this
            }
        }
    }

    private fun newFile() {
        currentFile = ""
        textArea.clear()
        //TODO("Implement confirmation dialog")
    }

    private fun openFile() {
        val fileToOpen = chooseFile(
            title = "Open file",
            filters = arrayOf(),
            mode = FileChooserMode.Single
        )
        if (fileToOpen.size != 0) {
            currentFile = fileToOpen[0].name
            textArea.text = fileToOpen[0].readText()
            saved = false
        }
    }

    private fun saveFile() {
        val fileToSave = chooseFile(
            title = "Save file",
            filters = arrayOf(),
            mode = FileChooserMode.Save
        )
        if (fileToSave.size != 0) {
            currentFile = fileToSave[0].name
            fileToSave[0].writeText(textArea.text)
            saved = true
        }
    }

}

fun main(args: Array<String>) {
    launch<KoditlinApp>(args)
}
