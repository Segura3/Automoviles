package com.seguras.automoviles.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.seguras.automoviles.databinding.AutoElementBinding
import com.seguras.automoviles.model.Auto

class AutoAdapter(contexto: Context, listAutos: ArrayList<Auto>): BaseAdapter() {

    private val listAutos = listAutos
    private val layoutInflater = LayoutInflater.from(contexto)

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
            tvCaballos.text = listAutos[p0].hp.toString()
            tvAno.text = listAutos[p0].year.toString()
            tvMarca.text = listAutos[p0].brand
            //Actualizar la imagen dependiendo de la marca
        }

        return binding.root
    }
}