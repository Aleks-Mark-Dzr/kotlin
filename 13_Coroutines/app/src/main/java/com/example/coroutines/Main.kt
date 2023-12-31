package com.example.coroutines

import android.annotation.SuppressLint
import kotlinx.coroutines.*
import java.math.BigInteger

object Fibonacci {
    suspend fun take(n: Int): BigInteger {
        if (n <= 0) {
            println("Неверная позиция: $n")
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
            println("Превышение времени вычисления позиции $n")
            throw t
        } catch (e: CancellationException) {
            println("Вычисление отменено на позиции $n")
            throw e
        }
        return a
    }
}


//@SuppressLint("SuspiciousIndentation")
fun main() {
    runBlocking {
        launch {
            for (i in 1..500) {
                if (i % 130 == 0)
                    println(".")
                else
                    delay(1)
                    print(".")
            }
        }
        launch {
            val n = 50
            val fibonacciNumber = Fibonacci.take(n)
            println("Число Фибоначчи на позиции $n: $fibonacciNumber")
        }
        launch {
            val n = 250
            val fibonacciNumber = Fibonacci.take(n)
            println("Число Фибоначчи на позиции $n: $fibonacciNumber")
        }
        launch {
            val n = -17
            val fibonacciNumber = Fibonacci.take(n)
            println("Число Фибоначчи на позиции $n: $fibonacciNumber")
        }
    }
}