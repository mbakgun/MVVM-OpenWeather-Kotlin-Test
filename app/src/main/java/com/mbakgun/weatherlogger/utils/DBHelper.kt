package com.mbakgun.weatherlogger.utils

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.mbakgun.weatherlogger.model.WeatherData
import java.util.*

/**
 * Created by burakakgun on 8/15/2018.
 */
class DBHelper(context: Context) : SQLiteOpenHelper(context, DBHelper.DATABASE_NAME, null, DBHelper.DATABASE_VERSION) {

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        Log.w("DB", "Upgrade from version $oldVersion to $newVersion")
        Log.w("DB", "This is version 1, no DB to update")
    }

    private val TABLE_NAME = "WeatherData"
    private val COL_DATE_CREATED = "dateCreated"
    private val COL_NAME = "name"
    private val COL_COUNTRY = "country"
    private val COL_DESCRIPTION = "description"
    private val COL_TEMP = "temp"
    private val COL_DT = "dt"

    companion object {
        private val DATABASE_NAME = "SQLITE_DATABASE"
        private val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE $TABLE_NAME ($COL_DATE_CREATED LONG PRIMARY KEY , $COL_NAME  VARCHAR(256),$COL_COUNTRY  VARCHAR(256),$COL_DESCRIPTION  VARCHAR(256),$COL_TEMP  DOUBLE, $COL_DT  VARCHAR(256))"
        db?.execSQL(createTable)
    }

    fun insertData(weatherData: WeatherData) {
        val sqliteDB = writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_DATE_CREATED, weatherData.dateCreated.time)
        contentValues.put(COL_NAME, weatherData.name)
        contentValues.put(COL_COUNTRY, weatherData.country)
        contentValues.put(COL_DESCRIPTION, weatherData.description)
        contentValues.put(COL_TEMP, weatherData.temp)
        contentValues.put(COL_DT, weatherData.dt)
        val result = sqliteDB.insert(TABLE_NAME, null, contentValues)
    }

    fun readData(): MutableList<WeatherData> {
        val weatherList = mutableListOf<WeatherData>()
        val sqliteDB = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val result = sqliteDB.rawQuery(query, null)
        if (result.moveToFirst()) {
            do {
                val weatherData = WeatherData(name = result.getString(result.getColumnIndex(COL_NAME)), country = result.getString(result.getColumnIndex(COL_COUNTRY)), description = result.getString(result.getColumnIndex(COL_DESCRIPTION)), temp = result.getDouble(result.getColumnIndex(COL_TEMP)), dt = result.getString(result.getColumnIndex(COL_DT)), dateCreated = Date(result.getLong(result.getColumnIndex(COL_DATE_CREATED))))
                weatherList.add(weatherData)
            } while (result.moveToNext())
        }
        result.close()
        sqliteDB.close()
        return weatherList
    }

    fun deleteAllData() {
        val sqliteDB = writableDatabase
        sqliteDB.delete(TABLE_NAME, null, null)
        sqliteDB.close()
    }
}