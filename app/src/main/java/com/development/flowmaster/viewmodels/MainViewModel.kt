package com.development.flowmaster.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import timber.log.Timber

class MainViewModel : ViewModel() {
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

    init {
        startCounterFlow()
    }


    private fun startCounterFlow() {
        viewModelScope.launch {
            counterFlow.collectLatest {
                Timber.tag("counter").d("counter at %s", it.toString())
            }
        }
    }
}