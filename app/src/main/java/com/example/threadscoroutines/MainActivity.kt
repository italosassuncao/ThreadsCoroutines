package com.example.threadscoroutines

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.threadscoroutines.R
import com.example.threadscoroutines.SegundaActivity
import com.example.threadscoroutines.databinding.ActivityMainBinding
import java.lang.Thread.currentThread
import java.lang.Thread.sleep

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private var pararThread = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        binding.buttonNT.setOnClickListener {
            startActivity(
                Intent(this, SegundaActivity::class.java)
            )
        }

        binding.buttonParar.setOnClickListener {
            pararThread = true
            binding.buttonIniciar.text = "Reiniciar execução"
            binding.buttonIniciar.isEnabled = true
        }

        binding.buttonIniciar.setOnClickListener {
            //MinhaThread().start()
            Thread(MinhaRunnable()).start()
            /*Thread{
                repeat(30) { indice ->
                    Log.i("TAG", "Executando: $indice T: ${currentThread().name}")
                    runOnUiThread {
                        binding.buttonIniciar.text = "Executando: $indice T: ${currentThread().name}"
                        binding.buttonIniciar.isEnabled = false
                        if (indice == 29){
                            binding.buttonIniciar.text = "Reiniciar execução"
                            binding.buttonIniciar.isEnabled = true
                        }
                    }
                    sleep(1000)
                }
            }*/
            /*repeat(15) { indice ->
                Log.i("TAG", "onCreate: $indice T: ${currentThread().name}")
                sleep(1000)
            }*/

            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
        }
    }

    inner class MinhaRunnable : Runnable {
        override fun run() {
            repeat(30) { indice ->

                if (pararThread) {
                    pararThread = false
                    return
                }

                Log.i("TAG", "Executando: $indice T: ${currentThread().name}")
                runOnUiThread {
                    binding.buttonIniciar.text = "Executando: $indice T: ${currentThread().name}"
                    binding.buttonIniciar.isEnabled = false
                    if (indice == 29){
                        binding.buttonIniciar.text = "Reiniciar execução"
                        binding.buttonIniciar.isEnabled = true
                    }
                }
                sleep(1000)
            }
        }

    }

    inner class MinhaThread : Thread() {
        override fun run() {
            super.run()

            repeat(30) { indice ->
                Log.i("TAG", "Executando: $indice T: ${currentThread().name}")
                runOnUiThread {
                    binding.buttonIniciar.text = "Executando: $indice T: ${currentThread().name}"
                    binding.buttonIniciar.isEnabled = false
                    if (indice == 29){
                        binding.buttonIniciar.text = "Reiniciar execução"
                        binding.buttonIniciar.isEnabled = true
                    }
                }
                sleep(1000)
            }
        }
    }
}