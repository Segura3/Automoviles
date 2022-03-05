package com.seguras.automoviles

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.seguras.automoviles.databinding.ActivityInsertBinding
import com.seguras.automoviles.db.DbAutos

class Insert : AppCompatActivity() {

    private lateinit var binding: ActivityInsertBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInsertBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val marcas = resources.getStringArray(R.array.marcas)

        val spinner = binding.spOpciones

        if(spinner != null){
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, marcas)
            spinner.adapter = adapter
            spinner.onItemSelectedListener = object:
            AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }
            }
        }
    }

    fun click(view: View) {
        val dbAutos = DbAutos(this)

        //Toast.makeText(this@Insert, "El valor spiner: ${binding.spOpciones.selectedItem}", Toast.LENGTH_SHORT).show()

        with(binding){
            if(!tietModelo.text.toString().isEmpty() && !tietHp.text.toString().isEmpty() && !tietAno.text.toString().isEmpty()) {
                val id = dbAutos.insertGame(
                    tietModelo.text.toString(), Integer.parseInt(tietHp.text.toString()),
                    Integer.parseInt(tietAno.text.toString()), spOpciones.selectedItem as String,
                )
                if (id > 0) { //El registro se inserto correctamente
                    Toast.makeText(
                        this@Insert,
                        "El registro se inserto correctamente",
                        Toast.LENGTH_SHORT
                    ).show()
                    //Reinicamos las cajas de texto
                    tietModelo.setText("")
                    tietHp.setText("")
                    tietAno.setText("")
                } else {
                    Toast.makeText(
                        this@Insert,
                        "Por favor llene todos los campos",
                        Toast.LENGTH_SHORT
                    ).show()
                    //Para mandar un error en una caja de texto especifica
                    //tietTitulo.text = "Por favor agrega un titulo"
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}