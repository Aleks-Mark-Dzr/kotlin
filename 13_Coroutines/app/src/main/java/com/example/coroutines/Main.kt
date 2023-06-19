package com.example.coroutines

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.math.BigInteger

object Fibonacci {
    suspend fun take(n: Int): Long {
        val sqrt5 = Math.sqrt(5.0)
        val phi = (1 + sqrt5) / 2

        return ((Math.pow(phi, n.toDouble()) - Math.pow(-phi, -n.toDouble())) / sqrt5).toLong()
    }
}


suspend fun main() {
    runBlocking {
        launch {
            val n = 5
            val fibonacciNumber = Fibonacci.take(n)
            println("Fibonacci number at position $n is: $fibonacciNumber")
        }
        launch {
            val n = 10
            val fibonacciNumber = Fibonacci.take(n)
            println("Fibonacci number at position $n is: $fibonacciNumber")
        }
        launch {
            val n = 15
            val fibonacciNumber = Fibonacci.take(n)
            println("Fibonacci number at position $n is: $fibonacciNumber")
        }
    }
}