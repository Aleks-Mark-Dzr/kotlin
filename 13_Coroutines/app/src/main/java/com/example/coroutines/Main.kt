package com.example.coroutines

import kotlinx.coroutines.*
import java.math.BigInteger

object Fibonacci {
    suspend fun take(n: Int): BigInteger = withContext(Dispatchers.Default) {
        if (n <= 0) {
            println("Неверная позиция: $n")
            return@withContext BigInteger.ZERO
        }

        var a = BigInteger.ZERO
        var b = BigInteger.ONE

            repeat(n ) {
                if (isActive){
                    yield()
                    println("Вычисление отменено на позиции $n")
                    return@withContext a
                }
                val next = a + b
                a = b
                b = next
            }
        a
    }
}


fun main() {
    runBlocking {
        launch {
            val n = 15
            val fibonacciNumber = Fibonacci.take(n)
            println("Число Фибоначчи на позиции $n: $fibonacciNumber")
        }
        launch {
            val n = 10
            val fibonacciNumber = Fibonacci.take(n)
            println("Число Фибоначчи на позиции $n: $fibonacciNumber")
        }
        launch {
            val n = -1
            val fibonacciNumber = Fibonacci.take(n)
            println("Число Фибоначчи на позиции $n: $fibonacciNumber")
        }
        delay(500) // Позволяет некоторое время для выполнения корутин
        coroutineContext.cancelChildren() // Отменить все корутины
    }
}