package com.disruptivestudio.pruebadsapp.ui

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.disruptivestudio.pruebadsapp.R
import com.disruptivestudio.pruebadsapp.databinding.ActivityDetailsBinding
import com.disruptivestudio.pruebadsapp.model.Utils
import java.io.ByteArrayOutputStream

class DetailsActivity : AppCompatActivity() {

    //Para instanciar los componentes de la Activity
    private lateinit var binding: ActivityDetailsBinding

    /**
     * Funcion principal de la Activity. Aqui se preparan los valores iniciales así como la asignación del Layout de la clase DetailsActivity
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()

        val extras = intent.extras
        val apartment_title = extras?.getString("apartment_title")
        val apartment_description = extras?.getString("apartment_description")
        val apartment_imagen = extras?.getString("apartment_imagen")

        binding.apartmentTitle.text = apartment_title
        binding.apartmentDescription.text = apartment_description
        val drawableResource = resources.getIdentifier(apartment_imagen, "drawable", packageName);
        if (drawableResource == 0) {
            binding.apartmentImage.setImageResource(R.drawable.departamento_1)
        } else {
            binding.apartmentImage.setImageResource(drawableResource)
        }

        /**
         * Capturar la pantalla completa del telefono y compartirla por algun servicio disponible en el telefono
         */
        binding.shareButton.setOnClickListener {
            binding.shareButton.isVisible = false
            val bitmap: Bitmap = screenShot(window.decorView.rootView)!!
            val share = Intent(Intent.ACTION_SEND)
            share.type = "image/png"
            val bytes = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bytes)
            val path = MediaStore.Images.Media.insertImage(
                contentResolver, bitmap, "Apartamento", null
            )
            val imageUri = Uri.parse(path)
            share.putExtra(Intent.EXTRA_STREAM, imageUri)
            startActivity(Intent.createChooser(share, "Compartir Detalles del Apartamento"))
            binding.shareButton.isVisible = true
        }

        binding.pagosButton.setOnClickListener {
            Utils.showAlertDialog(this, "Historial de Ventas ToDO")
        }

        binding.star1Button.setOnClickListener {
            Utils.showAlertDialog(this, "1 estrella ToDO")
        }

        binding.star2Button.setOnClickListener {
            Utils.showAlertDialog(this, "5 estrella ToDO")
        }
    }

    fun screenShot(view: View): Bitmap? {
        val bitmap = Bitmap.createBitmap(
            view.width, view.height, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }

    /**
     * Funcion para inicializar las variables de la Activity
     */
    private fun init() {
        val actionBar = supportActionBar
        actionBar?.setTitle("Detalles")
        actionBar?.setDisplayHomeAsUpEnabled(true)
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