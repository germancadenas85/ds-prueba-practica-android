package com.disruptivestudio.pruebadsapp.model

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.disruptivestudio.pruebadsapp.R
import com.disruptivestudio.pruebadsapp.data.Apartments

class CustomAdapter(
    private val mList: List<Apartments>,
    private val context: Context,
    private val onItemClicked: (position: Int) -> Unit
) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.apartment_layout, parent, false)
        return ViewHolder(view, onItemClicked)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ItemsViewModel = mList[position]
        val drawableResource = context.getResources()
            .getIdentifier(ItemsViewModel.imagen, "drawable", context.getPackageName());
        if (drawableResource == 0) {
            holder.apartment_image.setImageResource(R.drawable.departamento_1)
        } else {
            holder.apartment_image.setImageResource(drawableResource)
        }
        holder.apartment_title.text = ItemsViewModel.title
        holder.apartment_description.text = ItemsViewModel.descripcion
        holder.apartment_key.text = ItemsViewModel.key
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View, private val onItemClicked: (position: Int) -> Unit) :
        RecyclerView.ViewHolder(ItemView), View.OnClickListener {
        val apartment_image: ImageView = itemView.findViewById(R.id.apartment_image)
        val apartment_title: TextView = itemView.findViewById(R.id.apartment_title)
        val apartment_description: TextView = itemView.findViewById(R.id.apartment_description)
        var apartment_key: TextView = itemView.findViewById(R.id.apartment_key)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            val position = adapterPosition
            onItemClicked(position)
        }
    }
}