package com.seguras.automoviles.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.widget.Toast
import com.seguras.automoviles.R
import com.seguras.automoviles.model.Auto
import java.lang.Exception

class DbAutos(context: Context): DBHelper(context) {
    val context = context

    fun insertAuto(modelo: String, hp: String, ano: String, marca: String): Long{
        val dbHelper = DBHelper(context)
        val db = dbHelper.writableDatabase
        var id: Long = 0

        try{
            val values = ContentValues()
            values.put(context.getString(R.string.model), modelo)
            values.put(context.getString(R.string.hps), hp)
            values.put(context.getString(R.string.year), ano)
            values.put(context.getString(R.string.brand), marca)

            id = db.insert(TABLE_AUTOS, null, values)
        }catch(e: Exception){
            Toast.makeText(context, context.getString(R.string.errInser), Toast.LENGTH_SHORT).show()
        }finally {
            db.close()
        }

        return id
    }

    fun getAutos(): ArrayList<Auto>{
        val dbHelper = DBHelper(context)
        val db = dbHelper.writableDatabase

        var listAutos = ArrayList<Auto>()
        var autoTmp: Auto? = null
        var cursorAutos: Cursor? = null

        cursorAutos = db.rawQuery("SELECT * FROM $TABLE_AUTOS", null)

        if(cursorAutos.moveToFirst()){
            do{
                autoTmp = Auto(cursorAutos.getInt(0), cursorAutos.getString(1), cursorAutos.getString(2), cursorAutos.getString(3), cursorAutos.getString(4))
                listAutos.add(autoTmp)

            }while (cursorAutos.moveToNext())
        }
        cursorAutos.close()

        return listAutos
    }

    fun getAuto(id: Int): Auto?{
        val dbHelper = DBHelper(context)
        val db = dbHelper.writableDatabase

        var auto: Auto? = null
        var cursorAutos: Cursor? = null

        cursorAutos = db.rawQuery("SELECT * FROM $TABLE_AUTOS WHERE id = $id LIMIT 1", null)

        if(cursorAutos.moveToFirst()){
            auto = Auto(cursorAutos.getInt(0), cursorAutos.getString(1), cursorAutos.getString(2), cursorAutos.getString(3), cursorAutos.getString(4))
        }

        cursorAutos.close()

        return auto
    }

    fun updateAuto(id: Int, modelo: String, hp: String, ano: String, marca: String): Boolean{

        var banderaCorrecto = false

        val dbHelper = DBHelper(context)
        val db = dbHelper.writableDatabase

        try{
            db.execSQL("UPDATE $TABLE_AUTOS SET model = '$modelo', hp = '$hp', year = '$ano', brand = '$marca' WHERE id = $id")
            banderaCorrecto = true
        }catch(e: Exception){

        }finally {
            db.close()
        }

        return banderaCorrecto
    }

    fun deleteAuto(id: Int): Boolean{
        var banderaCorrecto = false

        val dbHelper = DBHelper(context)
        val db = dbHelper.writableDatabase

        try{
            db.execSQL("DELETE FROM $TABLE_AUTOS WHERE id = $id")
            banderaCorrecto = true
        }catch(e: Exception){

        }finally {
            db.close()
        }
        return banderaCorrecto
    }
}