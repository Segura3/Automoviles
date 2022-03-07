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
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                    when(position){
                        0 -> {
                            binding.ivHeader.setImageResource(R.drawable.bmw)
                            cleanErrors()
                        }
                        1 -> {
                            binding.ivHeader.setImageResource(R.drawable.nissan)
                            cleanErrors()
                        }
                        2 -> {
                            binding.ivHeader.setImageResource(R.drawable.toyota)
                            cleanErrors()
                        }
                        3 -> {
                            binding.ivHeader.setImageResource(R.drawable.volkswagen)
                            cleanErrors()
                        }
                    }
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {
                    binding.ivHeader.setImageResource(R.drawable.bmw)
                }
            }
        }
    }

    fun click(view: View) {
        val dbAutos = DbAutos(this)

        with(binding){
            if(validaCampos()){
                val id = dbAutos.insertAuto(
                    tietModelo.text.toString(), tietHp.text.toString(),
                    tietAno.text.toString(), spOpciones.selectedItem.toString(),
                )
                if (id > 0) {
                    Toast.makeText(
                        this@Insert,
                        getString(R.string.regIncorrecto),
                        Toast.LENGTH_SHORT
                    ).show()
                    tietModelo.setText(getString(R.string.empty))
                    tietHp.setText(getString(R.string.empty))
                    tietAno.setText(getString(R.string.empty))
                } else {
                    Toast.makeText(
                        this@Insert,
                        getString(R.string.errInsReg),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }else Toast.makeText(
                this@Insert,
                getString(R.string.empty),
                Toast.LENGTH_SHORT
            ).show()
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

    private fun cleanErrors(){
        with(binding){
            tietModelo.error = null
            tietModelo.setText(getString(R.string.empty))
            tietHp.error = null
            tietHp.setText(getString(R.string.empty))
            tietAno.error = null
            tietAno.setText(getString(R.string.empty))
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}