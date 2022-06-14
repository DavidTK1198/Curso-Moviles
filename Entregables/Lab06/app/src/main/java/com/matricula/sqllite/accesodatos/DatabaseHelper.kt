package com.matricula.sqllite.accesodatos

import android.content.Context
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

/**
 * Let's start by creating our database CRUD helper class
 * based on the SQLiteHelper.
 */
class DatabaseHelper(context: Context) :
        SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {

    /**
     * Our onCreate() method.
     * Called when the database is created for the first time. This is
     * where the creation of tables and the initial population of the tables
     * should happen.
     */
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE $TABLE_NAME_Estudiante (ID INTEGER PRIMARY KEY " +
                "AUTOINCREMENT,NOMBRE TEXT,APELLIDO TEXT,EDAD INTEGER)")

        db.execSQL("CREATE TABLE $TABLE_NAME_Curso(ID INTEGER PRIMARY KEY " +
                "AUTOINCREMENT,DESCRIPCION TEXT,CREDITOS INTEGER)")

        db.execSQL("CREATE TABLE Curso_Estudiante(ID INTEGER PRIMARY KEY " +
                "AUTOINCREMENT,ESTUDIANTE INTEGER,CURSO INTEGER,UNIQUE(ESTUDIANTE,CURSO) )")
    }

    /**
     * Let's create Our onUpgrade method
     * Called when the database needs to be upgraded. The implementation should
     * use this method to drop tables, add tables, or do anything else it needs
     * to upgrade to the new schema version.
     */
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_Estudiante)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_Curso)
        db.execSQL("DROP TABLE IF EXISTS " + "Curso_Estudiante")
        onCreate(db)
    }

    /**
     * Let's create our insertData() method.
     * It Will insert data to SQLIte database.
     */
    fun insertDataEstudiante(name: String, surname: String, edad: Int) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ESTUDIANTE_COL_2, name)
        contentValues.put(ESTUDIANTE_COL_3, surname)
        contentValues.put(ESTUDIANTE_COL_4, edad)
        db.insert(TABLE_NAME_Estudiante, null, contentValues)
    }
    fun insertDataCurso(codigo: Int, nombre: String, creditos: Int) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(CURSO_COL_1, codigo)
        contentValues.put(CURSO_COL_2, nombre)
        contentValues.put(CURSO_COL_3, creditos.toString())
        db.insert(TABLE_NAME_Curso, null, contentValues)
    }


    fun insertDataCurso_Estudiante(codigo: Int, ID: Int) {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("ESTUDIANTE", ID)
        contentValues.put("CURSO", codigo)
        db.insert("Curso_Estudiante", null, contentValues)
    }


//    /**
//     * Let's create  a method to update a row with new field values.
//     */
     fun updateDataEstudiante(id: String, name: String, surname: String, edad: String):Boolean {
      val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ESTUDIANTE_COL_1, id)
        contentValues.put(ESTUDIANTE_COL_2, name)
       contentValues.put(ESTUDIANTE_COL_3, surname)
        contentValues.put(ESTUDIANTE_COL_4, edad.toInt())
        db.update(TABLE_NAME_Estudiante, contentValues, "ID = ?", arrayOf(id))
        return true
    }
    fun updateDataCurso(codigo: Int, nombre: String, creditos: Int):Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(CURSO_COL_1, codigo)
        contentValues.put(CURSO_COL_2, nombre)
        contentValues.put(CURSO_COL_3, creditos)
        db.update(TABLE_NAME_Curso, contentValues, "ID = ?", arrayOf(codigo.toString()))
        return true
    }
//
//    /**
//     * Let's create a function to delete a given row based on the id.
//     */
   fun deleteDataEstudiante(id : String) : Int {
         val db = this.writableDatabase
        return db.delete(TABLE_NAME_Estudiante,"ID = ?", arrayOf(id))
     }
    fun deleteDataCurso(id : String) : Int {
        val db = this.writableDatabase
        return db.delete(TABLE_NAME_Curso,"ID = ?", arrayOf(id))
    }
//
//    /**
//     * The below getter property will return a Cursor containing our dataset.
//     */
    val allEstudiante : Cursor
        get() {
            val db = this.writableDatabase
            val res = db.rawQuery("SELECT * FROM " + TABLE_NAME_Estudiante, null)
            return res
        }
    val allCurso : Cursor
        get() {
            val db = this.writableDatabase
            val res = db.rawQuery("SELECT * FROM " + TABLE_NAME_Curso, null)
            return res
        }


    fun  allCurso_Estudiante(id:String):Cursor{

        val db = this.writableDatabase
        val res = db.rawQuery(
            "SELECT C.ID, C.DESCRIPCION, C.CREDITOS" +
                    "        FROM Curso_Estudiante AS D " +
                    "INNER JOIN curso_table C ON C.ID = D.CURSO" +
                    "        WHERE D.ESTUDIANTE = ?", arrayOf(id)
        )
        return res
    }

//
//    fun searchData (id: String) :Cursor
//    {
//        val db = this.writableDatabase
//        val querySearch = "SELECT * FROM " + TABLE_NAME + " WHERE ID = '"+id+"'"
//        val res = db.rawQuery(querySearch, null)
//        return res
//    }

    /**
     * Let's create a companion object to hold our static fields.
     * A Companion object is an object that is common to all instances of a given
     * class.
     */
    companion object {
        val DATABASE_NAME = "matricula.db"
        val TABLE_NAME_Estudiante = "estudiante_table"
        val TABLE_NAME_Curso = "curso_table"
        val ESTUDIANTE_COL_1 = "ID"
        val ESTUDIANTE_COL_2 = "NOMBRE"
        val ESTUDIANTE_COL_3 = "APELLIDO"
        val ESTUDIANTE_COL_4 = "EDAD"
        val CURSO_COL_1 = "ID"
        val CURSO_COL_2 = "DESCRIPCION"
        val CURSO_COL_3 = "CREDITOS"

    }
}
//end