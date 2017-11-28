package com.github.sursmobil

import com.github.sursmobil.components.app
import react.dom.render
import kotlin.browser.document


class MainApplication : ApplicationBase() {
    override val stateKeys: List<String>
        get() = emptyList()

    override fun start(state: Map<String, Any>) {
        val root = document.getElementById("root")
        render(root) {
            app()
        }
    }

    override fun dispose(): Map<String, Any> = emptyMap()
}