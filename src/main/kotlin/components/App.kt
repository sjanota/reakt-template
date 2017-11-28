package components

import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.h1


interface AppProps : RProps
interface AppState : RState

class App : RComponent<AppProps, AppState>() {
    override fun RBuilder.render() {
        h1 { +"Hello from React from Kotlin" }
    }

}