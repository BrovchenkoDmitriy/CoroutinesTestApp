package com.example.coroutinestestapp

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.coroutinestestapp.databinding.ActivityMainBinding
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.buttonDownload.setOnClickListener {
            lifecycleScope.launch {
                loadData()
            }
        }
    }

    private fun loadData() {
        binding.progressBar.isVisible = true
        binding.buttonDownload.isEnabled = false
        val city = loadCity()
        binding.tvLocation.text = city
        val temp = loadTemp(city)
        binding.tvTemperature.text = temp.toString()
        binding.progressBar.isVisible = false
        binding.buttonDownload.isEnabled = true
    }

    private suspend fun loadCity(): String {
        delay(5000)
        return "Moscow"
    }

    private suspend fun loadTemp(city: String): Int {
        Toast.makeText(
            this,
            "Loading temperature for city: $city",
            Toast.LENGTH_SHORT
        ).show()
        delay(5000)
        return 17
    }
}