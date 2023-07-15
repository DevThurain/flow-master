package com.development.flowmaster.presentation.state_flow_and_share_flow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class StateFlowAndShareFlowViewModel : ViewModel() {
    val _counterStateFlow = MutableStateFlow(0)
    val counterStateFlow = _counterStateFlow.asStateFlow()

    val _counterShareFlow = MutableSharedFlow<Int>()
    val counterShareFlow = _counterShareFlow.asSharedFlow()

    init {
        square(9)
        viewModelScope.launch {
            counterShareFlow.collect {
                Timber.tag("SHARE_FLOW").d(it.toString())
            }
        }
    }


    fun square(number: Int) {
        viewModelScope.launch {
            _counterShareFlow.emit(number * number)
        }
    }

    fun increment() {
        _counterStateFlow.value += 1
    }

}