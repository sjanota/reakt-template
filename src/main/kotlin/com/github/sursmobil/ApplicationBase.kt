package com.github.sursmobil


abstract class ApplicationBase<S> {
    abstract val stateKeys: List<String>

    abstract fun start(state: S)
    abstract fun dispose(): S
}