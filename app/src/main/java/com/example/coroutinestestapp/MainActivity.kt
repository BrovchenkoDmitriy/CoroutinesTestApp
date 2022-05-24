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
            loadData()
        }

    }

    private fun loadData() {
        binding.progressBar.isVisible = true
        binding.buttonDownload.isEnabled = false
       loadCity{
           binding.tvLocation.text = it
           loadTemp(it){
               binding.tvTemperature.text = it.toString()
               binding.progressBar.isVisible = false
               binding.buttonDownload.isEnabled = true
           }
       }
    }

    private fun loadCity(callBack:(String) -> Unit) {
        thread {
            Thread.sleep(5000)
            runOnUiThread {
                callBack.invoke("Moscow")
            }
        }
    }

    private fun loadTemp(city: String, callBack:(Int) -> Unit) {
        thread {
            Handler(Looper.getMainLooper()).post {
                Toast.makeText(
                    this,
                    "Loading temperature for city: $city",
                    Toast.LENGTH_SHORT
                ).show()
            }
            Thread.sleep(5000)
            Handler(Looper.getMainLooper()).post {
                callBack.invoke(17)
            }
        }
    }
}