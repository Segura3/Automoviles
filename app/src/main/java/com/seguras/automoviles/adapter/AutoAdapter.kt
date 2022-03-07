package com.seguras.automoviles.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.seguras.automoviles.R
import com.seguras.automoviles.databinding.AutoElementBinding
import com.seguras.automoviles.model.Auto

class AutoAdapter(contexto: Context, listAutos: ArrayList<Auto>): BaseAdapter() {

    private val listAutos = listAutos
    private val layoutInflater = LayoutInflater.from(contexto)
    val context = contexto

    override fun getCount(): Int {
        return listAutos.size
    }

    override fun getItem(p0: Int): Any {
        return listAutos[p0]
    }

    override fun getItemId(p0: Int): Long {
        return listAutos[p0].id.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val binding = AutoElementBinding.inflate(layoutInflater)

        with(binding){
            tvModelo.text = listAutos[p0].model
            tvCaballos.text = context.getString(R.string.hp) + listAutos[p0].hp
            tvAno.text = context.getString(R.string.modelo) + listAutos[p0].year
            tvMarca.text = listAutos[p0].brand

            when(listAutos[p0].brand){
                "BMW" -> {
                    ivMarca.setImageResource(R.drawable.bmw_logo)
                }
                "Nissan" -> {
                    ivMarca.setImageResource(R.drawable.nissan_logo)
                }
                "Toyota" -> {
                    ivMarca.setImageResource(R.drawable.toyota_logo)
                }
                "Volkswagen" -> {
                    ivMarca.setImageResource(R.drawable.volkswagen_logo)
                }
            }
        }

        return binding.root
    }
}