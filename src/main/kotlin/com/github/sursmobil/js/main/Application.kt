package com.github.sursmobil.js.main


abstract class Application<S> {
    abstract val stateKeys: List<String>

    abstract fun start(state: S)
    abstract fun dispose(): S
}