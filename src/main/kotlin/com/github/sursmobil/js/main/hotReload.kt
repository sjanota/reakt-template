package com.github.sursmobil.js.main

import kotlin.browser.document
import kotlin.dom.hasClass

fun <S> withHotReload(initState: S, appFactory: () -> Application<S>) {
    var application: Application<S>? = null

    val state: dynamic = module.hot?.let { hot ->
        hot.accept()

        hot.dispose { data ->
            data.appState = application?.dispose()
            application = null
        }

        hot.data
    }

    if (document.body != null) {
        application = start(initState, state, appFactory)
    } else {
        application = null
        document.addEventListener("DOMContentLoaded", { application = start(initState, state, appFactory) })
    }
}

fun <S> start(initState: S, state: dynamic, appFactory: () -> Application<S>): Application<S>? {
    return if (document.body?.hasClass("testApp") == true) {
        val application = appFactory()

        @Suppress("UnsafeCastFromDynamic")
        application.start(state?.appState ?: initState)

        application
    } else {
        null
    }
}