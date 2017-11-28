package com.github.sursmobil.components

import react.RBuilder
import react.RProps
import react.RState
import react.dom.h1


interface AppProps : RProps
interface AppState : RState

fun RBuilder.app() {
    h1 { +"Hello from React from Kotlin!" }
}
