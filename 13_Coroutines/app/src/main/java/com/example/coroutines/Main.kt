package com.example.coroutines

import kotlinx.coroutines.*
import java.math.BigInteger

object Fibonacci {
    suspend fun take(n: Int): BigInteger = withContext(Dispatchers.Default) {
        if (n <= 0) throw IllegalArgumentException("Неверная позиция: $n")

        var a = BigInteger.ZERO
        var b = BigInteger.ONE

        try {
            repeat(n ) {
                yield()
                val next = a + b
                a = b
                b = next
            }
        } catch (e: CancellationException) {
            println("Вычисление отменено на позиции $n")
            throw e
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
            val n = 0
            val fibonacciNumber = Fibonacci.take(n)
            println("Число Фибоначчи на позиции $n: $fibonacciNumber")
        }
        delay(500) // Позволяет некоторое время для выполнения корутин
        coroutineContext.cancelChildren() // Отменить все корутины
    }
}