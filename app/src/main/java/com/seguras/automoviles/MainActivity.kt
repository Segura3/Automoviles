package com.seguras.automoviles

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.seguras.automoviles.adapter.AutoAdapter
import com.seguras.automoviles.databinding.ActivityMainBinding
import com.seguras.automoviles.db.DBHelper
import com.seguras.automoviles.db.DbAutos
import com.seguras.automoviles.model.Auto

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var listAutos: ArrayList<Auto>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*val dbHelper = DBHelper(this)

        val db = dbHelper.writableDatabase

        if(db != null){
            Toast.makeText(this, "La DB fue creada exitosamente", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this, "Error al crear la DB", Toast.LENGTH_SHORT).show()
        }*/

        val dbAutos = DbAutos(this)

        listAutos = dbAutos.getAutos()

        if(listAutos.size == 0) binding.tvNoRegistros.visibility = View.VISIBLE
        else binding.tvNoRegistros.visibility = View.INVISIBLE

        val autosAdapter = AutoAdapter(this, listAutos)

        binding.lvAutos.adapter = autosAdapter

        binding.lvAutos.setOnItemClickListener { adapterView, view, i, l ->
            val intent = Intent(this, Details::class.java)
            intent.putExtra(getString(R.string.id), l.toInt())
            startActivity(intent)
            finish()
        }

    }

    fun click(view: View) {
        startActivity(Intent(this, Insert::class.java))
        finish()
    }
}