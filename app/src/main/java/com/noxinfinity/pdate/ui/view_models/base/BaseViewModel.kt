package com.noxinfinity.pdate.ui.view_models.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by merttoptas on 12.03.2022
 */

abstract class BaseViewModel<State : IViewState, Event : IViewEvent>  : ViewModel() {

    private val initialState: State by lazy { createInitialState() }
    abstract fun createInitialState(): State

    abstract fun onTriggerEvent(event: Event)

    private val _uiState: MutableStateFlow<State> = MutableStateFlow(initialState)
    val uiState: StateFlow<State> = _uiState

    val currentState: State get() = uiState.value

    private val _uiEvent: MutableSharedFlow<Event> = MutableSharedFlow()
    val uiEvent = _uiEvent.asSharedFlow()

    public fun setState(reduce: State.() -> State) {
        val newState = currentState.reduce()
        _uiState.value = newState
    }

    protected fun setEvent(event: Event) {
        viewModelScope.launch { _uiEvent.emit(event) }
    }

    protected suspend fun <T> call(
        callFlow: Flow<T>,
        completionHandler: (collect: T) -> Unit = {}
    ) {
        callFlow
            .catch { }
            .collect {
                completionHandler.invoke(it)
            }
    }


}

interface IViewState

interface IViewEvent