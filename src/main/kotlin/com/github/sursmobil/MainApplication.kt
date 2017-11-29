package com.github.sursmobil

import com.github.sursmobil.components.App
import com.github.sursmobil.redux.Redux
import com.github.sursmobil.redux.Redux.createStore
import react.dom.render
import kotlin.browser.document

sealed class Action
data class Increment(val n: Int): Action()
data class Decrement(val n: Int): Action()

fun Int.rootReducer(action: Action): Int {
    console.log(action)
    return when(action) {
        is Increment -> this + action.n
        is Decrement -> this - action.n
    }
}

class MainApplication : ApplicationBase<Int>() {
    lateinit var store: Redux.Store<Int>
    override val stateKeys: List<String>
        get() = emptyList()

    override fun start(state: Int) {
        val root = document.getElementById("root")
        store = createStore(state, Int::rootReducer)
        store.dispatch(Increment(4))
        console.log(store.getState())
        store.dispatch(Decrement(3))
        console.log(store.getState())
        render(root) {
            child(App::class) {}
        }
    }

    override fun dispose(): Int = store.getState()
}