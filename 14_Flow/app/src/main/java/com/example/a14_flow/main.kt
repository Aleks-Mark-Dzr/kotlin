package com.example.a14_flow

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.toList
import kotlinx.coroutines.flow.*
import kotlin.random.Random

fun main() {
    runBlocking {
        launch {
            LotoCardGenerator.usedNumber.collect {
                print("*$it ")
//                delay(500)
            }
        }
        launch {
            LotoCardGenerator.lotoNumber.collect {
                print("$it ")
//                delay(500)
            }
        }
    }
}


@OptIn(FlowPreview::class)
object LotoCardGenerator {
    private val scope = CoroutineScope(Job() + Dispatchers.Default)
    private val _lotoNumber = MutableSharedFlow<Int>()
    private val _usedNumber = MutableSharedFlow<Int>()
    val lotoNumber = _lotoNumber.asSharedFlow()
    val usedNumber = _usedNumber.asSharedFlow()

    init {
        scope.launch {
            repeat(15) {
                val randomNumber = Random.nextInt(1, 90)
                _lotoNumber.emit(randomNumber)
                _usedNumber.emit(randomNumber)

//                if (!(randomNumber in _usedNumber.produceIn(scope).toList())) {
//                    _lotoNumber.emit(randomNumber)
//                    _usedNumber.asSharedFlow()
//                }
                delay(100)
            }
        }
    }
}