package com.github.sursmobil.js.redux

import com.github.sursmobil.js.redux.ReduxNative.Store
import kotlin.reflect.KClass

typealias Middleware<S> = Store<S>.() -> ((Any) -> Any) -> (Any) -> Any
typealias Dispatch = (Any) -> Any
typealias GetState<S> = () -> S

val thunkMiddleware: dynamic = ReduxThunk.default

fun <S> thunk(f: Store<S>.() -> Any) = { dispatch: Dispatch, getState: GetState<S> ->
    val store = object : Store<S> {
        override fun dispatch(action: Any): Any = dispatch(action)
        override fun getState(): S = getState()
    }
    store.f()
}

inline fun <S, reified A : Any> createStore(init: S, noinline reducer: S.(A) -> S, middleWares: List<Middleware<S>> = emptyList()): Store<S> =
    createStoreInner(A::class, init, reducer, middleWares)

fun <S, A : Any> createStoreInner(actionType: KClass<A>, init: S, reducer: S.(A) -> S, middleWares: List<Middleware<S>> = emptyList()): Store<S> {
    val wrapper = { state: S, a: dynamic ->
        if (actionType.isInstance(a["action"].unsafeCast<A>())) {
            val origin = a["action"].unsafeCast<A>()
            state.reducer(origin)
        } else {
            state
        }
    }
    val native = ReduxNative.createStore(wrapper, init, ReduxNative.applyMiddleware(*(middleWares + actionTypeChecker()).toTypedArray()))
    return object : Store<S> by native {}
}

fun <S> logger(): Middleware<S> = { { next -> { action ->
    console.log("Action:", action)
    console.log("Before:", getState())
    val result = next(action)
    console.log("After:", getState())
    result
} } }

private fun <S> actionTypeChecker(): Middleware<S> = { { next -> { action ->
    val actionWrapper = js("({})")
    actionWrapper["action"] = action
    actionWrapper["type"] = action::class.simpleName
    next(actionWrapper as Any)
} } }


