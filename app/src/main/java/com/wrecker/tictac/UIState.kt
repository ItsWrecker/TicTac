package com.wrecker.tictac

data class UIState(
    val cells: MutableList<Triple<Boolean, Boolean, Boolean>> = Array(9) {
        Triple(false, false, true)
    }.toMutableList()
)