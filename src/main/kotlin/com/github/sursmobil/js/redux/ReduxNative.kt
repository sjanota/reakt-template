package com.github.sursmobil.js.redux

@JsModule("redux")
external object ReduxNative {
    fun <S> createStore(reducer: (S, dynamic) -> S, initialState: S = definedExternally, enhancer: dynamic = definedExternally): Store<S>

    interface Store<out S> {
        fun getState(): S
        fun dispatch(action: Any): Any
    }

    fun applyMiddleware(vararg middleware: dynamic): dynamic
}