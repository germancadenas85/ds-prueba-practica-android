package com.disruptivestudio.pruebadsapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import com.disruptivestudio.pruebadsapp.R
import com.disruptivestudio.pruebadsapp.model.Utils
import com.disruptivestudio.pruebadsapp.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

/**
 * Inicio de sesion por medio del correo electrónico
 */
class LoginActivity : AppCompatActivity() {

    //Para instanciar los componentes de la Activity
    private lateinit var binding: ActivityLoginBinding

    //Para instanciar los servicios de Firebase Authentication
    private lateinit var auth: FirebaseAuth

    /**
     * Funcion principal de la Activity. Aqui se preparan los valores iniciales así como la asignación del Layout de la clase LoginActivity
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()

        setup()

        listeners()
    }

    /**
     * Funcion para inicializar las variables de la Activity
     */
    private fun init() {
        auth = Firebase.auth
        val actionBar = supportActionBar
        actionBar?.hide()
    }

    /**
     * Funcion para configurar las variables de la Activity
     */
    private fun setup() {
        binding.loginProgress.isGone = true
        val user = Firebase.auth.currentUser
        user?.let {
            binding.emailInput.setText(it.email)
        }
    }

    /**
     * Agregar los eventos Click relqcionados con la Activity
     */
    private fun listeners() {
        binding.loginButton.setOnClickListener {
            login()
        }
        binding.registerText.setOnClickListener {
            register()
        }
        binding.forgotPasswordText.setOnClickListener {
            forgot()
        }
    }

    /**
     * Funcion login. Forzamos a que escriban su correo y contrasena (6 letras minimo)
     */
    private fun login() {
        val email_input = binding.emailInput.text.toString().trim()
        val password_input = binding.passwordInput.text.toString().trim()

        if (email_input.isEmpty() || password_input.isEmpty()) {
            Utils.showAlertDialog(
                this, getString(R.string.introducircorreoelectronicoyocontrasena)
            )
        } else {
            hideControls(true)
            auth.signInWithEmailAndPassword(email_input, password_input)
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
        binding.loginButton.isGone = hide
    }

    private fun register() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    private fun forgot() {
        val intent = Intent(this, ForgotActivity::class.java)
        startActivity(intent)
    }

    private fun updateUI(user: FirebaseUser?) {
        hideControls(false)
        if (user == null) {
            Utils.showAlertDialog(
                this, getString(R.string.elcorreoelectronicoyocontrasenasonincorrectos)
            )
        } else {
            if (user.isEmailVerified) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finishAffinity()
            } else {
                Utils.showAlertDialog(
                    this, "Es necesario que verifiques tu correo electrónico para poder ingresar."
                )
            }
        }
    }

}