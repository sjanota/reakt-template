package com.github.sursmobil

import kotlin.browser.document
import kotlin.dom.hasClass

fun main(args: Array<String>) {
    var application: MainApplication? = null

    val state: dynamic = module.hot?.let { hot ->
        hot.accept()

        hot.dispose { data ->
            data.appState = application?.dispose()
            application = null
        }

        hot.data
    }

    if (document.body != null) {
        application = start(state)
    } else {
        application = null
        document.addEventListener("DOMContentLoaded", { application = start(state) })
    }
}

fun start(state: dynamic): MainApplication? {
    return if (document.body?.hasClass("testApp") == true) {
        val application = MainApplication()

        @Suppress("UnsafeCastFromDynamic")
        application.start(state?.appState ?: 0)

        application
    } else {
        null
    }
}