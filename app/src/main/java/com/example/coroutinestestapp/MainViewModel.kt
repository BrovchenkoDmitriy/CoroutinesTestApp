package com.example.coroutinestestapp

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import kotlin.concurrent.thread

class MainViewModel : ViewModel() {

    private val parentJob = Job()

    private val coroutineScope = CoroutineScope(Dispatchers.Main + parentJob)

    fun method() {
        val childJob1 = coroutineScope.launch {
            delay(2000)
            Log.d(LOG_TAG, "First coroutines finished ")
        }
        val childJob2 = coroutineScope.launch {
            delay(5000)
            Log.d(LOG_TAG, "Second coroutines finished ")
        }
        thread {
            Thread.sleep(1000)
            parentJob.cancel()
            Log.d(LOG_TAG, "Parent job is active: ${parentJob.isActive}")
        }
        Log.d(LOG_TAG, "${parentJob.children.contains(childJob1)}")
        Log.d(LOG_TAG, "${parentJob.children.contains(childJob2)}")
    }

    override fun onCleared() {
        super.onCleared()
        coroutineScope.cancel()
    }
    companion object {
        private const val LOG_TAG = "MainViewModel"
    }
}