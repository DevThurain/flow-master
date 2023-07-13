package com.development.flowmaster.presentation.flow_counter

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retry

class FlowCounterViewModel : ViewModel() {
    val counterFlow = flow<Int> {
        val startingCount = 10
        var currentCount = startingCount
        emit(currentCount)
        while (currentCount > 0) {
            delay(1000)
            currentCount--
            emit(currentCount)
        }
    }


    fun startCounterFlow() : Flow<Int> {
        return flow<Int> {
            val startingCount = 10
            var currentCount = startingCount
            emit(currentCount)
            while (currentCount > 0) {
                delay(1000)
                currentCount--
                emit(currentCount)
            }
        }
    }
}