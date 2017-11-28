package com.github.sursmobil

import com.github.sursmobil.components.App
import react.dom.render
import kotlin.browser.document


class MainApplication : ApplicationBase() {
    override val stateKeys: List<String>
        get() = emptyList()

    override fun start(state: Map<String, Any>) {
        val root = document.getElementById("root")
        render(root) {
            child(App::class) {}
        }
    }

    override fun dispose(): Map<String, Any> = emptyMap()
}