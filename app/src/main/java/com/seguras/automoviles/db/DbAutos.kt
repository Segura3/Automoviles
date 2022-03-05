package com.seguras.automoviles.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.widget.Toast
import com.seguras.automoviles.model.Auto
import java.lang.Exception

class DbAutos(context: Context): DBHelper(context) {
    val context = context

    fun insertGame(modelo: String, hp: Int, año: Int, marca: String): Long{
        val dbHelper = DBHelper(context)
        val db = dbHelper.writableDatabase
        var id: Long = 0

        try{
            val values = ContentValues()
            values.put("model", modelo)
            values.put("hp", hp)
            values.put("year", año)
            values.put("brand", marca)

            id = db.insert(TABLE_AUTOS, null, values)
        }catch(e: Exception){
            Toast.makeText(context, "Error de insercion", Toast.LENGTH_SHORT).show()
        }finally {
            db.close()
        }

        return id
    }

    fun getGames(): ArrayList<Auto>{
        val dbHelper = DBHelper(context)
        val db = dbHelper.writableDatabase

        var listGames = ArrayList<Auto>()
        var gameTmp: Auto? = null
        var cursorGames: Cursor? = null

        cursorGames = db.rawQuery("SELECT * FROM $TABLE_AUTOS", null)

        if(cursorGames.moveToFirst()){
            do{
                gameTmp = Auto(cursorGames.getInt(0), cursorGames.getString(1), cursorGames.getInt(2), cursorGames.getInt(3), cursorGames.getString(4))
                listGames.add(gameTmp)

            }while (cursorGames.moveToNext())
        }
        cursorGames.close()

        return listGames
    }

    fun getGame(id: Int): Auto?{
        val dbHelper = DBHelper(context)
        val db = dbHelper.writableDatabase

        var game: Auto? = null
        var cursorGames: Cursor? = null

        cursorGames = db.rawQuery("SELECT * FROM $TABLE_AUTOS WHERE id = $id LIMIT 1", null)

        if(cursorGames.moveToFirst()){
            game = Auto(cursorGames.getInt(0), cursorGames.getString(1), cursorGames.getInt(2), cursorGames.getInt(3), cursorGames.getString(4))
        }

        cursorGames.close()

        return game
    }

    fun updateGame(id: Int, modelo: String, hp: Int, año: Int, marca: String): Boolean{

        var banderaCorrecto = false

        val dbHelper = DBHelper(context)
        val db = dbHelper.writableDatabase

        try{
            db.execSQL("UPDATE $TABLE_AUTOS SET model = '$modelo', hp = '$hp', year = '$año', brand = '$marca' WHERE id = $id")
            banderaCorrecto = true
        }catch(e: Exception){

        }finally {
            db.close()
        }

        return banderaCorrecto
    }

    fun deleteGame(id: Int): Boolean{
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