package com.example.coroutinestestapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.coroutinestestapp.databinding.ActivityMainBinding
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch



class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel.method()
        binding.buttonDownload.setOnClickListener {
            binding.progressBar.isVisible = true
            binding.buttonDownload.isEnabled = false
            val deferredCity: Deferred<String> = lifecycleScope.async {
                val city = loadCity()
                binding.tvLocation.text = city
                city
            }
            val deferredTemp: Deferred<Int> = lifecycleScope.async {
                val temp = loadTemp()
                binding.tvTemperature.text = temp.toString()
                temp
            }
            lifecycleScope.launch {
                val city = deferredCity.await()
                val temp = deferredTemp.await()

                binding.progressBar.isVisible = false
                binding.buttonDownload.isEnabled = true
                Toast.makeText(
                    this@MainActivity,
                    "City $city  Temp $temp",
                    Toast.LENGTH_SHORT
                ).show()
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