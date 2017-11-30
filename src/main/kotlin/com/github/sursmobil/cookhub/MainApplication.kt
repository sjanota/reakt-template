package com.github.sursmobil.cookhub

import com.github.sursmobil.cookhub.components.App
import com.github.sursmobil.js.main.Application
import com.github.sursmobil.js.redux.Redux
import com.github.sursmobil.js.redux.Redux.createStore
import react.dom.render
import kotlin.browser.document

sealed class Action
data class Increment(val n: Int): Action()
data class Decrement(val n: Int): Action()
data class SetString(val s: String): Action()

data class State(
    val n: Int,
    val s: String
)

fun State.rootReducer(action: Action): State = State(
    n = n.reducer(action),
    s = s.reducer(action)
)

fun Int.reducer(action: Action) = when(action) {
    is Increment -> this + action.n
    is Decrement -> this - action.n
    else -> this
}

fun String.reducer(action: Action) = when(action) {
    is SetString -> action.s
    else -> this
}

class MainApplication : Application<State>() {
    override val initState: State = State(0, "")
    lateinit var store: Redux.Store<State>

    override fun start(state: State) {
        val root = document.getElementById("root")
        store = createStore(state, State::rootReducer)

        store.dispatch(Increment(4))
        console.log(store.getState(), "aaa")

        store.dispatch(Decrement(3))
        console.log(store.getState())

        val a = Increment(3)
        a.asDynamic()["n"] = 5
        store.dispatch(a)
        console.log(store.getState())

        render(root) {
            child(App::class) {}
        }
    }

    override fun dispose(): State = store.getState()
}