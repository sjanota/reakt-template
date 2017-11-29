package com.github.sursmobil.cookhub.components

import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.h1

class App : RComponent<App.Props, App.State>() {
    interface Props : RProps
    interface State : RState

    override fun RBuilder.render() {
        h1 { +"Hello from React from Kotlin!" }
    }

}
