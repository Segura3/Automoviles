package com.seguras.automoviles

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.seguras.automoviles.databinding.ActivityEditBinding
import com.seguras.automoviles.db.DbAutos
import com.seguras.automoviles.model.Auto

class Edit : AppCompatActivity() {

    private lateinit var binding: ActivityEditBinding

    private lateinit var dbAutos: DbAutos
    var auto: Auto? = null
    var id: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(savedInstanceState == null){
            val bundle = intent.extras
            if(bundle != null){
                id = bundle.getInt(getString(R.string.id), 0)
            }
        }else{
            id = savedInstanceState.getSerializable(getString(R.string.id)) as Int
        }

        dbAutos = DbAutos(this)
        auto = dbAutos.getAuto(id)

        if(auto != null){
            with(binding){
                tietModelo.setText(auto?.model)
                tietHp.setText(auto?.hp)
                tietAno.setText(auto?.year)
            }
        }

        val marcas = resources.getStringArray(R.array.marcas)
        val spinner = binding.spOpciones
        if(spinner != null){
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, marcas)
            spinner.adapter = adapter
            spinner.setSelection(adapter.getPosition(auto?.brand))
            spinner.onItemSelectedListener = object:
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                    when(position){
                        0 -> {
                            binding.ivHeader.setImageResource(R.drawable.bmw)
                        }
                        1 -> {
                            binding.ivHeader.setImageResource(R.drawable.nissan)
                        }
                        2 -> {
                            binding.ivHeader.setImageResource(R.drawable.toyota)
                        }
                        3 -> {
                            binding.ivHeader.setImageResource(R.drawable.volkswagen)
                        }
                    }
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {

                }
            }
        }
    }

    fun click(view: View) {
        with(binding){
            if(validaCampos()){
                if(dbAutos.updateAuto(id, tietModelo.text.toString(),
                        tietHp.text.toString(), tietAno.text.toString(),
                        spOpciones.selectedItem.toString())){
                    Toast.makeText(this@Edit, getString(R.string.regActEx), Toast.LENGTH_LONG).show()
                    val intent = Intent(this@Edit, Details::class.java)
                    intent.putExtra(getString(R.string.id), id)
                    startActivity(intent)
                    finish()
                }else{
                    Toast.makeText(this@Edit, getString(R.string.errActReg), Toast.LENGTH_LONG).show()
                }
            }else{
                Toast.makeText(
                    this@Edit,
                    getString(R.string.ingresaValReq),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun validaCampos(): Boolean{
        var res = true

        with(binding){
            if(tietModelo.text.toString().isEmpty()){
                tietModelo.error = getString(R.string.valReq)
                res = false
            }else tietModelo.error = null
            if(tietHp.text.toString().isEmpty()){
                tietHp.error = getString(R.string.valReq)
                res = false
            }else tietHp.error = null
            if(tietAno.text.toString().isEmpty()){
                tietAno.error = getString(R.string.valReq)
                res = false
            }else tietAno.error = null
        }
        return res
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}