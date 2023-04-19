package com.disruptivestudio.pruebadsapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.view.isGone
import com.disruptivestudio.pruebadsapp.R
import com.disruptivestudio.pruebadsapp.model.Utils
import com.disruptivestudio.pruebadsapp.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {

    //Para instanciar los componentes de la Activity
    private lateinit var binding: ActivityRegisterBinding

    //Para instanciar los servicios de Firebase Authentication
    private lateinit var auth: FirebaseAuth

    /**
     * Funcion principal de la Activity. Aqui se preparan los valores iniciales así como la asignación del Layout de la clase RegisterActivity
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()

        setup()

        listeners()
    }

    /**
     * Funcion para inicializar las variables de la Activity
     */
    private fun init() {
        val actionBar = supportActionBar
        actionBar?.setTitle(getString(R.string.registrar))
        actionBar?.setDisplayHomeAsUpEnabled(true)

        auth = Firebase.auth
    }

    /**
     * Funcion para configurar las variables de la Activity
     */
    private fun setup() {
        binding.loginProgress.isGone = true
    }

    private fun listeners() {
        binding.registerButton.setOnClickListener {
            register()
        }
    }

    private fun register() {
        val email_input = binding.emailInput.text.toString().trim()
        val password_input = binding.passwordInput.text.toString().trim()
        val confirm_password_input = binding.confirmPasswordInput.text.toString().trim()

        if (email_input.isEmpty()) {
            Utils.showAlertDialog(
                this, "Introduce tu correo electrónico"
            )
            binding.emailInput.requestFocus()
        } else if (password_input.isEmpty()) {
            Utils.showAlertDialog(
                this, "Introduce tu contraseña"
            )
            binding.passwordInput.requestFocus()
        } else if (!password_input.equals(confirm_password_input)) {
            Utils.showAlertDialog(
                this, "Las contraseñas no coinciden"
            )
            binding.confirmPasswordInput.requestFocus()
        } else if (password_input.length < 6) {
            Utils.showAlertDialog(
                this, "La contraseña debe tener al menos 6 caracteres"
            )
            binding.passwordInput.requestFocus()
        } else {
            hideControls(true)
            auth.createUserWithEmailAndPassword(email_input, password_input)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val user = auth.currentUser
                        updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        updateUI(null)
                    }
                }
        }
    }

    private fun hideControls(hide: Boolean) {
        binding.loginProgress.isGone = !hide
        binding.registerButton.isGone = hide
    }

    private fun updateUI(user: FirebaseUser?) {
        hideControls(false)
        if (user == null) {
            Utils.showAlertDialog(
                this, getString(R.string.noselogrorealizarelregistrointentedenuevo)
            )
        } else {
            Utils.showAlertDialog(
                this,
                "El registro ha sido realizado correctamente. Es necesario que verifiques tu correo electrónico: " + user.email
            )
            user.sendEmailVerification()
            finish()
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