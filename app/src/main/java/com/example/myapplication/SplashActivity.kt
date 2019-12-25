package com.example.myapplication

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.example.myapplication.DBHelper.DBHelper
import com.example.myapplication.Models.Song


class SplashActivity : Activity() {

    lateinit var db : DBHelper
    var songsList:List<Song> = ArrayList<Song>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splashfile)

        db= DBHelper(this)
        AppPreferences.init(this)

        //If it is the first run of the app, initialize the database
        if (!AppPreferences.firstRun) {
            AppPreferences.firstRun = true
            /**MAKE TRIGGER TUTORIAL
            */
            //Add all songs objects to the database.
            songsList=db.allSongs
            for(item in songsList ){
                println(item.name)
            }
            var current=applicationContext.assets.list("current")?.asList()
            var k=current!!.size-1
            for(i in 0..k){
                addSong(this,i,current[i],"current",db)
            }
            var classic=applicationContext.assets.list("classic")?.asList()
            var k2=classic!!.size-1
            for(j in 0..k2){
                addSong(this,k+j+1,classic[j],"classic",db)
            }
        }

        //Load Main class after the loading time.
        var handler = object : Handler() {}
        handler.postDelayed({
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }

    //Add songs objects to the database.
    private fun addSong(context: Context,id: Int,songName: String,mode: String,db: DBHelper) {
        var fullName=songName
        var songNameN=songName.replace('_',' ')
        var song= Song(id,songNameN.substring(songNameN.indexOf('(')+1,songNameN.indexOf(')')),songNameN.substring(0,songNameN.indexOf('(')),mode,0,fullName)
        db.addSong(song)
    }



}
