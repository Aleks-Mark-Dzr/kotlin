package com.example.coroutines

import kotlinx.coroutines.*
import java.math.BigInteger

object Fibonacci {
    suspend fun take(n: Int): BigInteger = coroutineScope {
        if (n <= 0) {
            println("Неверная позиция: $n")
            return@coroutineScope BigInteger.ZERO
        }

        var a = BigInteger.ZERO
        var b = BigInteger.ONE

        try {
            withTimeout(3000L) {
                repeat(n) {
                    yield()
                    val next = a + b
                    a = b
                    b = next
                    delay(20)
                }
            }
        } catch (t: TimeoutCancellationException) {
            println("Превышение времени вычисления")
            throw t
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
            for (i in 1..5000) {
                if (i % 130 == 0)
                    println(".")
                else
                    delay(1)
                    print(".")
            }
        }
        launch {
            val n = 15
            delay(5000)
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