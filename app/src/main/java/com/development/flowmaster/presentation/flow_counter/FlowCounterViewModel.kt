package com.development.flowmaster.presentation.flow_counter

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.fold
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.reduce
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.launch
import timber.log.Timber

class FlowCounterViewModel : ViewModel() {
    val countDownFlow = flow<Int> {
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
//        countCountDownFlow()
//        reduceCountDownFlow()
//        flatNumberFlow()
        bufferFlow()
    }

    fun startCounterFlow(): Flow<Int> {
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

    private fun countCountDownFlow() {
        viewModelScope.launch {
            val totalEvenCount = countDownFlow.map {
                it * it
            }.onEach {
                Timber.tag("counter").d("current time is : $it")
            }.filter {
                it % 2 == 0
            }.onEach {
                Timber.tag("counter").d("current modified even time is : $it")
            }.count()

            Timber.tag("counter").d("Total even number count is : $totalEvenCount")
        }
    }

    private fun reduceCountDownFlow() {
        viewModelScope.launch {
            val reducedResult = countDownFlow.onEach {
                Timber.tag("counter").d("current time is : $it")
            }.reduce { accumulator, value -> accumulator + value }

            val reducedResultFold = countDownFlow.onEach {
                Timber.tag("counter").d("current time is : $it")
            }.fold(initial = 100) { accumulator, value -> accumulator + value }

            Timber.tag("counter").d("Reduced Result is : $reducedResultFold")
        }
    }

    // You can use flatMerge in real world.
    // emit user movie list from db.
    // when network call is complete emit updated movie list
    // flatConcat is used for database operations which need to complete in order
    private fun flatNumberFlow() {
        val numberListFlow = flow<Int> {
            emit(1)
            emit(2)
            emit(3)
            emit(4)
            emit(5)
        }

        viewModelScope.launch {
            numberListFlow.flatMapMerge {
                flow<Int> {
                    emit(it * 10)
                    delay(2000)
                    emit(it * 100)
                }
            }.collectLatest {
                Timber.tag("flatCountDownFlow").d(it.toString())
            }
        }
    }


    // buffer() run normally
    // conflate() skip dish2 if dish1 is not finished and dish 3 is ready
    // collectLatest() skip finishing dish if other dish is ready.
    private fun bufferFlow() {
        val dishFlow = flow<String> {
            emit("Dish 1")
            delay(1000)
            emit("Dish 2")
            delay(100)
            emit("Dish 3")
        }

        viewModelScope.launch {
            dishFlow.onEach {
                Timber.tag("dish").d("$it is arrived.")
            }
                .conflate()
                .collectLatest{
                Timber.tag("dish").d("eating $it")
                delay(2000)
                Timber.tag("dish").d("finished eating $it")
            }
        }
    }

}