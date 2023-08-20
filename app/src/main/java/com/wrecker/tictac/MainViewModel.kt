package com.wrecker.tictac

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val state = MutableStateFlow(UIState())
    val _state: StateFlow<UIState> = state.asStateFlow()

    fun selectCell(id: Int) = viewModelScope.launch {
        val newState = state.value.cells.toMutableList()
        newState[id] = Triple(true, false, false)
        val emptyIndices = newState.withIndex()
            .filter { it.value.third }
            .map { it.index }

        if (emptyIndices.isNotEmpty()) {
            val randomIndex = emptyIndices.random()
            newState[randomIndex] = Triple(false, true, false)
        }

        state.emit(UIState(newState))
    }


}