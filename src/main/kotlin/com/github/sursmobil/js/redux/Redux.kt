package com.github.sursmobil.js.redux

object Redux {
    class Store<out S>(
        private val native: ReduxNative.Store<S>
    ) : ReduxNative.Store<S> by native {
        override fun dispatch(action: Any) {
            val wrapper = js("({})")
            wrapper["action"] = action
            wrapper["type"] = action::class.simpleName
            native.dispatch(wrapper)
        }
    }

    fun <S, A> createStore(init: S, reducer: S.(A) -> S): Redux.Store<S> {
        val wrapper = { state: S, a: dynamic ->
            val origin = a["action"].unsafeCast<A>()
            state.reducer(origin)
        }
        val native = ReduxNative.createStore(wrapper, init)
        return Store(native)
    }
}

