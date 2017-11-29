package com.github.sursmobil.cookhub

import com.github.sursmobil.js.main.withHotReload


fun main(args: Array<String>) {
    withHotReload(0) {
        MainApplication()
    }
}