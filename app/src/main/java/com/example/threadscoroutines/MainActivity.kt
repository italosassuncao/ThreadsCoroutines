package com.example.threadscoroutines

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.threadscoroutines.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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
            //Thread(MinhaRunnable()).start()
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

            CoroutineScope(Dispatchers.IO).launch { // Execuçao com IO

                /*repeat(15) { indice ->
                    Log.i("info_coroutine", "Executando: $indice T: ${currentThread().name}")
                    withContext(Dispatchers.Main){ // Execuçao Main
                        binding.buttonIniciar.text = "Executando: $indice T: ${currentThread().name}"
                    }
                    delay(1000)

                }*/
                executar()

            }

            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
        }
    }

    private suspend fun executar(){
        val usuario = recuperarUsuarioLogado()
        Log.i("info_coroutine", "Usuario: ${usuario.usuario} T: ${currentThread().name}")
        val postagens = recuperarPostagensId(usuario.id)
        Log.i("info_coroutine", "Postagens: ${postagens.size} T: ${currentThread().name}")
    }

    private suspend fun recuperarPostagensId(idUsuario: Int): List<String> {
        delay(2000)
        return listOf(
            "Postagem 1",
            "Postagem 2",
            "Postagem 3"
        )
    }

    private suspend fun recuperarUsuarioLogado(): Usuario {
        delay(1000)
        return Usuario(1, "Italo")

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