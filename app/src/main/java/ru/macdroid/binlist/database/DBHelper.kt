package ru.macdroid.binlist.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import ru.macdroid.binlist.model.db.DBModel
import ru.macdroid.binlist.utils.Constants.DATABASE_NAME
import ru.macdroid.binlist.utils.Constants.DATABASE_VERSION
import ru.macdroid.binlist.utils.Constants.KEY_BIN_VALUE
import ru.macdroid.binlist.utils.Constants.KEY_ID
import ru.macdroid.binlist.utils.Constants.TABLE_NAME

class DBHelper(val context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {

        val createTableBin = (
                "create table " + TABLE_NAME + " ( " +
                        KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
                        KEY_BIN_VALUE + " TEXT NOT NULL " +
                        " ) "
                )

        db?.execSQL(createTableBin)
    }

    override fun onUpgrade(db: SQLiteDatabase?, old: Int, new: Int) {

        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertIntoDb(bin_value: Long) : Long {

        val db = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(KEY_BIN_VALUE, bin_value)
        val success = db.insert(TABLE_NAME, null, contentValues)

        db.close()
        return success
    }

    fun getBinList() : List<DBModel> {

        val listOfBin = mutableListOf<DBModel>()
        val db = this.readableDatabase
        val selectQuery = "SELECT * FROM $TABLE_NAME"
        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: Exception) {
            e.printStackTrace()
            db.execSQL(selectQuery)
            return listOfBin
        }

        var id: Int
        var bin: String

        if ((cursor != null) && cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID))
                bin = cursor.getString(cursor.getColumnIndexOrThrow(KEY_BIN_VALUE))

                val binAdd = DBModel(
                    id = id,
                    bin = bin
                )

                listOfBin.add(binAdd)

            } while (cursor.moveToNext())
        }

        cursor.close()

        return listOfBin.reversed()
    }

}