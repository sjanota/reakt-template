package com.github.sursmobil.cookhub

import com.github.sursmobil.cookhub.components.App
import com.github.sursmobil.js.main.Application
import com.github.sursmobil.js.redux.*
import com.github.sursmobil.js.redux.ReduxNative.Store
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
    n = n.nReducer(action),
    s = s.sReducer(action)
)

fun Int.nReducer(action: Action) = when(action) {
    is Increment -> this + action.n
    is Decrement -> this - action.n
    else -> this
}

fun String.sReducer(action: Action) = when(action) {
    is SetString -> action.s
    else -> this
}

class MainApplication : Application<State>() {
    override val initState: State = State(0, "")
    lateinit var store: Store<State>

    override fun start(state: State) {
        val root = document.getElementById("root")
        store = createStore(state, State::rootReducer, listOf(
            logger<State>(),
            thunkMiddleware
        ))

        store.dispatch(Increment(4))
        store.dispatch(Decrement(3))
        store.dispatch(thunk<State> {
            dispatch(Increment(3))
            dispatch(SetString("Test redux-thunk"))
        })

        render(root) {
            child(App::class) {}
        }
    }

    override fun dispose(): State = store.getState()
}