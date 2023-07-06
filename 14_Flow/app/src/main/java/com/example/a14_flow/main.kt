package com.example.a14_flow

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.random.Random

fun main() {
    runBlocking {
        launch {
            LotoCardGenerator.lotoNumber.collect {
                print("$it ")
            }
        }
    }
}


object LotoCardGenerator {
    private val scope = CoroutineScope(Job() + Dispatchers.Default)
    private val _lotoNumber = MutableSharedFlow<Int>()
//    private val _usedNumber = MutableSharedFlow<Int>()
    val lotoNumber = _lotoNumber.asSharedFlow()
//    val usedNumber = _usedNumber.asSharedFlow()

    init {
        scope.launch {
            repeat(15) {
                _lotoNumber.emit(Random.nextInt(1, 90))
                delay(200)
            }
        }
    }
}