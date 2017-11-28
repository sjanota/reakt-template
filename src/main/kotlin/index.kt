import components.App
import react.dom.render
import kotlin.browser.document

fun main(args: Array<String>) {
    val root = document.getElementById("root")
    render(root) {
        App()
    }
}