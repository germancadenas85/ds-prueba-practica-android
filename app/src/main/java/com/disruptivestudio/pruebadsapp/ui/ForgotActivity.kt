package com.disruptivestudio.pruebadsapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.disruptivestudio.pruebadsapp.R
import com.disruptivestudio.pruebadsapp.model.Utils
import com.disruptivestudio.pruebadsapp.databinding.ActivityForgotBinding

class ForgotActivity : AppCompatActivity() {

    //Para instanciar los componentes de la Activity
    private lateinit var binding: ActivityForgotBinding

    /**
     * Funcion principal de la Activity. Aqui se preparan los valores iniciales así como la asignación del Layout de la clase ForgotActivity
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()

        listeners()
    }

    /**
     * Funcion para inicializar las variables de la Activity
     */
    private fun init() {
        val actionBar = supportActionBar
        actionBar?.setTitle(getString(R.string.recuperar_contrase_a))
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun listeners() {
        binding.resetPasswordButton.setOnClickListener {
            Utils.showAlertDialog(
                this, "ToDO :)"
            )
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}