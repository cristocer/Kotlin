package com.example.myapplication


import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.content.Context
import android.content.Intent
import com.example.myapplication.Models.Song
import com.example.myapplication.DBHelper.DBHelper
import java.io.InputStream
import android.view.animation.AnimationUtils
import android.app.Activity
import android.view.*
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GuessActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener  {

    lateinit var db : DBHelper
    var songsList:List<Song> = ArrayList<Song>()
    private var guessedN = 0
    private var guessedA = 0
    private var guessU="guess"
    lateinit var songg:Song
    var songId: Int=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share)
        //refreshData()
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)
        //title
        setTitle("?-?                   Search for lyrics --->")

        //song format
        db= DBHelper(this)
        songsList=db.allSongs
        var songMode=AppPreferences.songMode

        if(songMode.equals("classic")){
            songId+=20
        }

        try {
            //coming from mycollection
            songId = getIntent().extras!!.getInt("song")
            if(songMode.equals("classic")){
                songId+=20
            }

        }catch (e:KotlinNullPointerException){
            //coming from main activity
            //current first 20
            if(songMode.equals("current")){
                var songI=0
                for (item in songsList){
                    if(item.mode.equals("current")&&item.attempted==0){
                        songI=item.id
                        break
                    }
                }
                songId = songI
            }else {
                var songI = 20
                for (item in songsList) {
                    if (item.mode.equals("classic") && item.attempted == 0) {
                        songI = item.id
                        break
                    }
                }
                songId = songI
            }
        }
        songg=songsList.get(songId)
        songg.attempted=1
        db.updateSong(songg)
        if(songg.guessName==1&&songg.guessArtist==1){
            setTitle("${songg.artist!!.take(15)}-${songg.name!!.take(15)}")
            guessedA=1
            guessedN=1
        }else if (songg.guessArtist==1){
            setTitle("${songg.artist!!.take(15)}-?")
            guessedA=1

        }else if (songg.guessName==1){
            setTitle("?-${songg.name!!.take(15)}")
            guessedN=1
        }


        var fullName:String=songsList.get(songId).fullName
        var textModelArrayList: ArrayList<String> = populateList(fullName,songId)
        val recyclerView = findViewById<View>(R.id.my_recycler_view2) as RecyclerView
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        var mAdapter = MyAdapter2(textModelArrayList)
        mAdapter.setOnItemClickListener(object : MyAdapter2.ClickListener {
            override fun onClick(pos: Int, aView: View) {

            }
        })
        recyclerView.adapter = mAdapter

        //val v1 = window.decorView.rootView
        //guess buttton

        val playNowButton: Button = findViewById(R.id.guess_butt)
        // set on-click listener
        playNowButton.setOnClickListener {
            // your code to perform when the user clicks on the button
            if(songsList.get(songId).lyricsC==0){
                Toast.makeText(this@GuessActivity, "You need to collect at least one lyric before guessing", Toast.LENGTH_SHORT).show()
            }else {
                if (guessedN == 0) {
                    guessLayout("Song Name")
                }
                if (guessedA == 0) {
                    guessLayout("Song Artist(2 spaces between artists)")
                }
            }
        }

        //button in the toolbar to go to search for lyrics
        toolbar.setOnClickListener {
            // your code to perform when the user clicks on the button
            val intentToStartMapsActivity = Intent(this, MapsActivity::class.java)
            var lyricsTotal = loadJson(this, songsList.get(songId).fullName)!!.split('\n')
            intentToStartMapsActivity.putExtra("markers", (lyricsTotal.size - songsList.get(songId).lyricsC))
            intentToStartMapsActivity.putExtra("image", AppPreferences.IMAGES[songId])
            startActivityForResult(intentToStartMapsActivity, 10)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data:
    Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 10) {
            if (data!!.hasExtra("lyrics")) {
                /*Toast.makeText(this, data.extras!!.getString("lyrics"),
                    Toast.LENGTH_SHORT)
                    .show()*/
                songsList.get(songId).lyricsC+=data.extras!!.getInt("lyrics")
                db.updateSong(songsList.get(songId))
                var fullName:String=songsList.get(songId).fullName
                var textModelArrayList: ArrayList<String> = populateList(fullName,songId)
                val recyclerView = findViewById<View>(R.id.my_recycler_view2) as RecyclerView
                val layoutManager = LinearLayoutManager(this)
                recyclerView.layoutManager = layoutManager
                var mAdapter = MyAdapter2(textModelArrayList)
                mAdapter.setOnItemClickListener(object : MyAdapter2.ClickListener {
                    override fun onClick(pos: Int, aView: View) {
                        //cool animation
                        val animation = AnimationUtils.loadAnimation(applicationContext, R.anim.rotate)
                        aView.startAnimation(animation)
                    }
                })
                recyclerView.adapter = mAdapter
            }
        }
    }

    private fun loadJson(context: Context,songName: String): String? {
        var input: InputStream? = null
        var jsonString: String

        try {
            // Create InputStream
            input = context.assets.open(songName)

            val size = input.available()

            // Create a buffer with the size
            val buffer = ByteArray(size)

            // Read data from InputStream into the Buffer
            input.read(buffer)

            // Create a json String
            jsonString = String(buffer)
            return jsonString
        } catch (ex: Exception) {
            ex.printStackTrace()
        } finally {
            // Must close the stream
            input?.close()
        }

        return null
    }
    private fun populateList(fullName:String,songId:Int): ArrayList<String> {
        //list with String objects
        val list = ArrayList<String>()
        db= DBHelper(this)
        songsList=db.allSongs
        var lyrics=loadJson(this,fullName)!!.split('\n')
        var lyricsCollected=songsList.get(songId).lyricsC

        if(lyricsCollected>=lyrics.size){
            var i:ImageView=findViewById(R.id.congrats_im)
            i.visibility=View.VISIBLE
            Thread.sleep(2000)
            lyricsCollected=lyrics.size
            i.visibility=View.INVISIBLE
        }
        for (lyric in 0..lyricsCollected-1) {
            list.add(lyrics[lyric])
        }
        for (lyric in 0..lyrics.size-1-lyricsCollected) {
            list.add("?")
        }
        return list
    }
    private fun guessLayout(title:String){
        //nu uita sa updates guess in db!!!
        var builder:AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        // I'm using fragment here so I'm using getView() to provide ViewGroup
        // but you can provide here any other instance of ViewGroup from your Fragment / Activity
        var viewInflated:View = LayoutInflater.from(this).inflate(R.layout.guess_layout,null)
        //var viewInflated:View = LayoutInflater.from(this).inflate(R.layout.guess_layout,  (android.R.id.content).getRootView(), false);
        //
        // Set up the input
        var input: EditText =  viewInflated.findViewById(R.id.input)
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        builder.setView(viewInflated)

        // Set up the buttons
        builder.setPositiveButton(android.R.string.ok) { dialog, p1 ->
            val guesss = input.text
            var isValid = true
            if (guesss.isBlank()) {
                input.error = "Try again!"//getString(R.string.validation_empty)
                isValid = false
            }

            if (isValid) {
                guessU = input.text.toString()
                if(title.equals("Song Name")) {
                    if (guessU.equals(songg.name)) {
                        songg.guessName = 1
                        db.updateSong(songg)
                        refreshTitle(songg)
                        //println("2"+songg.name+"-"+songg.id+"-"+songg.artist+"-"+songg.guessName+"-"+songg.guessArtist+"-"+songg.attempted)
                    }
                }else{
                    if(guessU.equals(songg.artist)){
                        songg.guessArtist=1
                        db.updateSong(songg)
                        //guessedA=1
                        refreshTitle(songg)
                    }
                }
            }

            if (isValid) {
                dialog.dismiss()
            }
        }

        builder.setNegativeButton(android.R.string.cancel) { dialog, p1 ->
            dialog.cancel()
        }

        builder.show();
    }
    private fun refreshTitle(songg:Song){
        //Thread.sleep(10000)
        if(songg.guessName==1&&songg.guessArtist==1){
            setTitle("${songg.artist!!.take(15)}-${songg.name!!.take(15)}")
            guessedA=1
            guessedN=1
        }else if (songg.guessArtist==1){
            setTitle("${songg.artist!!.take(15)}-?")
            guessedA=1

        }else if (songg.guessName==1){
            setTitle("?-${songg.name!!.take(15)}")
            guessedN=1
        }
    }
    //If the menu is open while back button is pressed then close it
    //otherwise act as a normal back button event
    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_home -> {
                // Handle the camera action
                val intentToStartDetailActivity = Intent(this, MainActivity::class.java)
                startActivity(intentToStartDetailActivity)
            }
            R.id.nav_gallery -> {
                val intentToStartDetailActivity = Intent(this, Mycollection::class.java)
                startActivity(intentToStartDetailActivity)
            }
            R.id.nav_slideshow -> {
                val intentToStartDetailActivity = Intent(this, Achievements::class.java)
                startActivity(intentToStartDetailActivity)
            }
            R.id.nav_tools -> {
                val intentToStartDetailActivity = Intent(this, SettingsActivity::class.java)
                startActivity(intentToStartDetailActivity)
            }
            R.id.nav_share -> {
                val intentToStartDetailActivity = Intent(this, Share::class.java)
                startActivity(intentToStartDetailActivity)
            }
            R.id.nav_send -> {
                val intentToStartDetailActivity = Intent(this, Feedback::class.java)
                startActivity(intentToStartDetailActivity)
            }
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}
