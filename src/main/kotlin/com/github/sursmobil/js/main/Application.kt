package com.github.sursmobil.js.main

import kotlin.browser.document


abstract class Application<S> {
    abstract val initState: S
    abstract fun start(state: S)
    abstract fun dispose(): S

    fun run() {
        val state: dynamic = module.hot?.let { hot ->
            hot.accept()

            hot.dispose { data ->
                data.appState = this.dispose()
            }

            hot.data
        }

        fun start() {
            @Suppress("UnsafeCastFromDynamic")
            start(state?.appState ?: initState)
        }

        if (document.body != null) {
            start()
        } else {
            document.addEventListener("DOMContentLoaded", { start() })
        }
    }
}