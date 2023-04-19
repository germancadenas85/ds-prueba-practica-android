package com.disruptivestudio.pruebadsapp.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isGone
import androidx.recyclerview.widget.GridLayoutManager
import com.disruptivestudio.pruebadsapp.data.Apartments
import com.disruptivestudio.pruebadsapp.databinding.ActivityMainBinding
import com.disruptivestudio.pruebadsapp.model.CustomAdapter
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    //Para instanciar los componentes de la Activity
    private lateinit var binding: ActivityMainBinding
    private lateinit var database: DatabaseReference
    private var list = ArrayList<Apartments>()
    private var listFull = ArrayList<Apartments>()
    private var recomendations = false

    /**
     * Funcion principal de la Activity. Aqui se preparan los valores iniciales así como la asignación del Layout de la clase MainActivity
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance().getReference("apartments_table")

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    list.clear()
                    for (element in dataSnapshot.children) {
                        val apartment = element.getValue(Apartments::class.java)
                        apartment?.key = element.key
                        list.add(apartment!!)
                        listFull.add(apartment!!)
                    }
                    updateUI()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        }
        database.addValueEventListener(postListener)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateUI() {
        binding.mainProgress.isGone = true
        binding.recyclerview.layoutManager = GridLayoutManager(this, 2)
        val adapter =
            CustomAdapter(list, applicationContext, { position -> onListItemClick(position) })
        binding.recyclerview.adapter = adapter

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                list.clear()
                list.addAll(listFull.filter { find ->
                    find.title.lowercase()
                        .contains(query.lowercase()) || find.descripcion.lowercase().contains(
                        query.lowercase()
                    )
                })
                adapter.notifyDataSetChanged()
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

        binding.searchView.setOnCloseListener(object : SearchView.OnCloseListener {
            override fun onClose(): Boolean {
                list.clear()
                list.addAll(listFull)
                adapter.notifyDataSetChanged()
                binding.titleMain.text = "Apartamentos"
                return false
            }
        })

        binding.recomendationButton.setOnClickListener {
            list.clear()
            if (!recomendations) {
                list.addAll(listFull.filter { find ->
                    find.recomendation == 1
                })
                recomendations = true
                binding.titleMain.text = "Recomendados"
            } else {
                list.addAll(listFull)
                recomendations = false
                binding.titleMain.text = "Apartamentos"
            }
            adapter.notifyDataSetChanged()
        }
    }

    private fun onListItemClick(position: Int) {
        val apartment = list[position]
        val intent = Intent(this, DetailsActivity::class.java).apply {
            putExtra("apartment_title", apartment.title)
            putExtra("apartment_description", apartment.descripcion)
            putExtra("apartment_imagen", apartment.imagen)
        }
        startActivity(intent)
    }
}