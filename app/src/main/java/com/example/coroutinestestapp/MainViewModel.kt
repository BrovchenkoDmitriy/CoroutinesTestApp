package com.example.coroutinestestapp

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import java.lang.RuntimeException
import kotlin.concurrent.thread

class MainViewModel : ViewModel() {

    private val parentJob = SupervisorJob()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.d(LOG_TAG, "Exception caught $throwable")
    }


    private val coroutineScope = CoroutineScope(
        Dispatchers.Main + parentJob + exceptionHandler
    )


    fun method() {

        val childJob1 = coroutineScope.launch {
            delay(3000)
            Log.d(LOG_TAG, "First coroutines finished ")
        }

        val childJob2 = coroutineScope.launch {
            val childJob4 = coroutineScope.async {
                delay(2000)
                error()
                Log.d(LOG_TAG, "Fourth coroutines finished ")

            }
            delay(2500)
            Log.d(LOG_TAG, "Second coroutines finished ")
        }

        val childJob3: Job = coroutineScope.launch {
            delay(1000)
            Log.d(LOG_TAG, "Third coroutines finished ")
        }


    }

    private fun error() {
        throw RuntimeException()
    }

    override fun onCleared() {
        super.onCleared()
        coroutineScope.cancel()
    }

    companion object {
        private const val LOG_TAG = "MainViewModel"
    }
}