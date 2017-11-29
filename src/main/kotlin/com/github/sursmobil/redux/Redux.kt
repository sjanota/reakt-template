package com.github.sursmobil.redux

object Redux {
    class Store<S>(
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
        val wrapper = { s: S?, a: dynamic ->
            val state = s ?: init
            val origin = a["action"].unsafeCast<A>()
            if (origin == null) {
                state
            } else {
                state.reducer(origin)
            }
        }
        val native = ReduxNative.createStore(wrapper)
        return Store(native)
    }
}

