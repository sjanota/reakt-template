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

    fun <S, A> createStore(init: S, reducer: S.(A) -> S, middleWares: List<dynamic> = emptyList()): Redux.Store<S> {
        val wrapper = { state: S, a: dynamic ->
            if (a["action"] is A) {
                val origin = a["action"].unsafeCast<A>()
                state.reducer(origin)
            } else {
                throw Exception("Unsupported action $a")
            }
        }
        val native = ReduxNative.createStore(wrapper, init)
        return Store(native)
    }
}

