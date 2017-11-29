package com.github.sursmobil.redux

@JsModule("redux")
external object ReduxNative {
    fun <S> createStore(reducer: (S?, dynamic) -> S): Store<S>

    interface Store<out S> {
        fun getState(): S
        fun dispatch(action: dynamic)
    }
}