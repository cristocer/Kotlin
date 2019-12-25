package com.example.myapplication.DBHelper
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.myapplication.Models.Song
import java.util.ArrayList
class DBHelper (context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        // If you change the database schema, you must increment the database version.
        val DATABASE_VERSION = 1
        val DATABASE_NAME = "FeedReader.db"
        val TABLE_NAME="Songs"
        val COL_ID="Id"
        val COL_NAME="Name"
        val COL_ARTIST="Artist"
        val COL_MODE="Mode"
        val COL_ATTEMPTED="Attempted"
        val COL_FULLNAME="Fullname"
        val COL_GUESSSONG="Songname"
        val COL_GUESSARTSIST="Artistname"
        val COL_LYRICSCOLLECTED="LyricsCollected"

    }
    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_TABLE_ENTRIES =("CREATE TABLE $TABLE_NAME ($COL_ID INTEGER PRIMARY KEY,$COL_NAME TEXT,$COL_ARTIST TEXT,$COL_MODE TEXT,$COL_ATTEMPTED INTEGER,$COL_FULLNAME TEXT,$COL_GUESSSONG INTEGER,$COL_GUESSARTSIST INTEGER,$COL_LYRICSCOLLECTED INTEGER)" )
        db!!.execSQL(CREATE_TABLE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db!!)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    //CRUD
    val allSongs: ArrayList<Song>
        get(){
            val songs = ArrayList<Song>()
            val selectQuery="SELECT * FROM $TABLE_NAME"
            val db = this.writableDatabase
            var cursor: Cursor = db.rawQuery(selectQuery,null)

            if (cursor.moveToFirst()) {
                do{
                    val song=Song()
                    song.id=cursor.getInt(cursor.getColumnIndex(COL_ID))
                    song.name=cursor.getString(cursor.getColumnIndex(COL_NAME))
                    song.artist=cursor.getString(cursor.getColumnIndex(COL_ARTIST))
                    song.mode=cursor.getString(cursor.getColumnIndex(COL_MODE))
                    song.attempted=cursor.getInt(cursor.getColumnIndex(COL_ATTEMPTED))
                    song.fullName=cursor.getString(cursor.getColumnIndex(COL_FULLNAME))
                    song.guessName=cursor.getInt(cursor.getColumnIndex(COL_GUESSSONG))
                    song.guessArtist=cursor.getInt(cursor.getColumnIndex(COL_GUESSARTSIST))
                    song.lyricsC=cursor.getInt(cursor.getColumnIndex(COL_LYRICSCOLLECTED))
                    songs.add(song)
                }while(cursor.moveToNext())
            }
            db.close()
            return songs
        }

    @Throws(SQLiteConstraintException::class)
    fun addSong(song: Song){
        // Gets the data repository in write mode
        val db = this.writableDatabase

        // Create a new map of values, where column names are the keys
        val values = ContentValues()
        values.put(COL_ID, song.id)
        values.put(COL_NAME, song.name)
        values.put(COL_ARTIST, song.artist)
        values.put(COL_MODE, song.mode)
        values.put(COL_ATTEMPTED, song.attempted)
        values.put(COL_FULLNAME, song.fullName)
        values.put(COL_GUESSSONG, song.guessName)
        values.put(COL_GUESSARTSIST, song.guessArtist)
        values.put(COL_LYRICSCOLLECTED, song.lyricsC)
        // Insert the new row, returning the primary key value of the new row
        db.insert(TABLE_NAME, null, values)
        db.close()
    }



    @Throws(SQLiteConstraintException::class)
    fun updateSong(song: Song): Int{
        // Gets the data repository in write mode
        val db = this.writableDatabase

        // Create a new map of values, where column names are the keys
        val values = ContentValues()
        values.put(COL_ID, song.id)
        values.put(COL_NAME, song.name)
        values.put(COL_ARTIST, song.artist)
        values.put(COL_MODE, song.mode)
        values.put(COL_ATTEMPTED, song.attempted)
        values.put(COL_FULLNAME, song.fullName)
        values.put(COL_GUESSSONG, song.guessName)
        values.put(COL_GUESSARTSIST, song.guessArtist)
        values.put(COL_LYRICSCOLLECTED, song.lyricsC)
        // Insert the new row, returning the primary key value of the new row
        return db.update(TABLE_NAME, values, "$COL_ID=?",arrayOf(song.id.toString()))

    }

    @Throws(SQLiteConstraintException::class)
    fun deleteSong(song: Song) {
        // Gets the data repository in write mode
        val db = this.writableDatabase

        // Insert the new row, returning the primary key value of the new row
        db.delete(TABLE_NAME, "$COL_ID=?",arrayOf(song.id.toString()))
        db.close()
    }

}