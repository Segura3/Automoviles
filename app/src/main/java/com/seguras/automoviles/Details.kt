package com.seguras.automoviles

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.seguras.automoviles.databinding.ActivityDetailsBinding
import com.seguras.automoviles.db.DbAutos
import com.seguras.automoviles.model.Auto

class Details : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding
    private lateinit var dbAutos: DbAutos
    var auto: Auto? = null
    var id: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
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
                when(auto?.brand){
                    getString(R.string.bmw) -> {
                        ivHeader.setImageResource(R.drawable.bmw)
                    }
                    getString(R.string.nissan) -> {
                        ivHeader.setImageResource(R.drawable.nissan)
                    }
                    getString(R.string.toyoya) -> {
                        ivHeader.setImageResource(R.drawable.toyota)
                    }
                    getString(R.string.vw) -> {
                        ivHeader.setImageResource(R.drawable.volkswagen)
                    }
                }
                tietMarca.setText(auto?.brand)
                tietModelo.setText(auto?.model)
                tietHp.setText(auto?.hp)
                tietAno.setText(auto?.year)

                tietMarca.inputType = InputType.TYPE_NULL
                tietModelo.inputType = InputType.TYPE_NULL
                tietHp.inputType = InputType.TYPE_NULL
                tietAno.inputType = InputType.TYPE_NULL
            }
        }
    }

    fun click(view: View) {
        when(view.id){
            R.id.btnEdit ->{
                val intent = Intent(this, Edit::class.java)
                intent.putExtra(getString(R.string.id), id)
                startActivity(intent)
                finish()
            }
            R.id.btnDelete ->{
                AlertDialog.Builder(this)
                    .setTitle(getString(R.string.confirmacion))
                    .setMessage(getString(R.string.dElimAut,auto?.model))
                    .setPositiveButton(getString(R.string.si), DialogInterface.OnClickListener { dialogInterface, i ->
                        if(dbAutos.deleteAuto(id)){
                            Toast.makeText(this, getString(R.string.autoElim), Toast.LENGTH_LONG).show()
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        }
                    })
                    .setNegativeButton(getString(R.string.no), DialogInterface.OnClickListener { dialogInterface, i ->  })
                    .show()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}

