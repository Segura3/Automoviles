package com.seguras.automoviles.db

import android.content.Context
import android.content.res.Resources
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.seguras.automoviles.MainActivity
import com.seguras.automoviles.R

open class DBHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    private val ctx = context

    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL("CREATE TABLE $TABLE_AUTOS (id INTEGER PRIMARY KEY AUTOINCREMENT, model TEXT NOT NULL, hp TEXT NOT NULL, year TEXT NOT NULL, brand TEXT NOT NULL)")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0?.execSQL("DROP TABLE $TABLE_AUTOS")
        onCreate(p0)
    }

    companion object{
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "automoviles.db"
            //MainActivity.instance.getString(R.string.autodb)
            //Resources.getSystem().getString(android.R.string.autodb)
        public const val TABLE_AUTOS = "autos"
    }
}