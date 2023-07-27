package com.example.a14_flow

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.random.Random

data class Player(val name: String, val card: List<List<Int>>)

class LotoGame(private val players: List<Player>) {

    private val numbersFlow = MutableSharedFlow<Int>()

    init {
        // Генерируем случайные числа и отправляем их в Flow
        generateRandomNumbers()
    }

    private fun generateRandomNumbers() = runBlocking {
        coroutineScope {
            launch {
                repeat(90) {
                    // Генерируем случайное число от 1 до 90
                    val randomNumber = Random.nextInt(1, 91)
                    numbersFlow.emit(randomNumber)
                    delay(1000) // Задержка в 1 секунду между числами
                }
            }
        }
    }

    fun startGame() = runBlocking {
        coroutineScope {
            players.forEach { player ->
                launch {
                    // Следим за числами из Flow и проверяем на выигрыш
                    numbersFlow.collect { number ->
                        if (player.card.flatten().contains(number)) {
                            println("${player.name} выиграл(а) число $number в ${System.currentTimeMillis()}")
                        }
                    }
                }
            }
        }
    }
}

fun main() {
    val player1 = Player("Игрок 1", generateRandomCard())
    val player2 = Player("Игрок 2", generateRandomCard())

    val game = LotoGame(listOf(player1, player2))
    game.startGame()
}

fun generateRandomCard(): List<List<Int>> {
    val card = mutableListOf<List<Int>>()
    val numbers = mutableListOf<Int>()
    for (i in 1..90) {
        numbers.add(i)
    }
    numbers.shuffle()

    repeat(3) {
        val row = numbers.subList(it * 9, (it + 1) * 9).sorted().take(5)
        card.add(row)
    }

    return card
}