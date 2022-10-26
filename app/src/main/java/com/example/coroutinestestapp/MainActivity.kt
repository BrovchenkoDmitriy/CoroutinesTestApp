package com.example.coroutinestestapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.coroutinestestapp.databinding.ActivityMainBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch



class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.buttonDownload.setOnClickListener {
            binding.progressBar.isVisible = true
            binding.buttonDownload.isEnabled = false
            val jobCity = lifecycleScope.launch {
                val city = loadCity()
                binding.tvLocation.text = city
            }
            val jobTemp = lifecycleScope.launch {
                val temp = loadTemp()
                binding.tvTemperature.text = temp.toString()
            }
            lifecycleScope.launch {
                jobCity.join()
                jobTemp.join()
                binding.progressBar.isVisible = false
                binding.buttonDownload.isEnabled = true
            }
        }
    }

    private suspend fun loadData() {
        binding.progressBar.isVisible = true
        binding.buttonDownload.isEnabled = false
        val city = loadCity()
        binding.tvLocation.text = city
        val temp = loadTemp()
        binding.tvTemperature.text = temp.toString()
        binding.progressBar.isVisible = false
        binding.buttonDownload.isEnabled = true
    }

    private suspend fun loadCity(): String {
        delay(5000)
        return "Moscow"
    }

    private suspend fun loadTemp(): Int {
        delay(3000)
        return 17
    }
}