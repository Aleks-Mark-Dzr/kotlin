package com.example.streams

import android.util.Log
import kotlin.concurrent.thread

private val tag = "main"

fun main() {
    LoadHelper.load()
    println("test output to consol from main")
    Log.d(tag, "main was called")
}

object LoadHelper {
    private var progress: Int = 0

    private fun startLoading() {
        thread {
            while (progress < 100) {
                progress++
                Log.d(tag, "startLoading was called")
                Thread.sleep(500)
                println("output to consol from startLoading")
            }
        }
    }


    private fun updateProgressBar() {
        thread {
            while (progress < 100) {
                println(getProgressString())
                Log.d(tag, "updataProgressBar was called")
                Thread.sleep(100)
            }
        }
    }

    private fun getProgressString(): String {
        val str = StringBuilder()
        for (i in 0..100) {
            if (i < progress)
                str.append("#")
            else str.append(".")
            Log.d(tag, "StringBuilder was called")
        }
        str.append(" $progress %")
        return str.toString()
    }

    fun load() {
        startLoading()
        updateProgressBar()
        println("output to consol from load")
        Log.d(tag, "load was called")
    }
}