package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import android.view.MenuItem
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.DBHelper.DBHelper
import com.example.myapplication.Models.MyModel
import com.example.myapplication.Models.Song
import io.ghyeok.stickyswitch.widget.StickySwitch

class Mycollection : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var db : DBHelper
    var songsList:List<Song> = ArrayList<Song>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
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

        //recyceviewAAAA!!!
        //add icon parameter
        var imageModelArrayList: ArrayList<MyModel> = populateList()
        val recyclerView = findViewById<View>(R.id.my_recycler_view) as RecyclerView
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        var mAdapter = MyAdapter(imageModelArrayList)

        //launch the guess activity
        mAdapter.setOnItemClickListener(object : MyAdapter.ClickListener {
            override fun onClick(pos: Int, aView: View) {

                //PUT HERE THE GUESS ACTIVITY
                var intent = Intent(aView.context, GuessActivity::class.java)
                intent.putExtra("song",pos)
                //extras.gettring(key)
                startActivity(intent)

            }
        })
        recyclerView.adapter = mAdapter



        //change mode current-classic
        val stickySwitch: StickySwitch = findViewById(R.id.sticky_switch)
        stickySwitch.setRightIcon(R.mipmap.neww)
        stickySwitch.setLeftIcon(R.mipmap.oldd)
        if(AppPreferences.songMode.equals("classic")){
            stickySwitch.setDirection(StickySwitch.Direction.LEFT)
        }else{
            stickySwitch.setDirection(StickySwitch.Direction.RIGHT)
        }
        stickySwitch.onSelectedChangeListener = object : StickySwitch.OnSelectedChangeListener {
            override fun onSelectedChange(direction: StickySwitch.Direction, text: String) {
                //Change mode from current to classic and vice-versa
                //Change in preferences as well.
                //Refresh the view
                //Log.d("ssss","Now Selected : " + direction.name + ", Current Text : " + text)
                if (direction == StickySwitch.Direction.LEFT) {
                    AppPreferences.songMode="classic"
                } else {
                    AppPreferences.songMode="current"
                }
                imageModelArrayList = populateList()
                mAdapter = MyAdapter(imageModelArrayList)
                mAdapter.setOnItemClickListener(object : MyAdapter.ClickListener {
                    override fun onClick(pos: Int, aView: View) {
                        //PUT HERE THE GUESS ACTIVITY
                        var intent = Intent(aView.context, GuessActivity::class.java)
                        intent.putExtra("song",pos)
                        //extras.gettring(key)
                        startActivity(intent)

                    }
                })
                recyclerView.adapter = mAdapter
            }
        }

    }



    //Here do the population with the data.
    private fun populateList(): ArrayList<MyModel> {
        //list with MyModel objects
        val list = ArrayList<MyModel>()
        //mode of the songs in the list
        var songMode=AppPreferences.songMode
        //array with references to images
        val myImageList=AppPreferences.IMAGES
        //array with the names of the songs to be displayed
        val myImageNameList: MutableList<String> = ArrayList()
        //array of indexes for the corresponding song images
        val imagesIndex:MutableList<Int> = ArrayList()

        db= DBHelper(this)
        songsList=db.allSongs
        //list of songs attempted from the corresponding mode
        for(item in songsList ){
            if(item.attempted==1&&item.mode!!.equals(songMode)){
                if(item.guessArtist==1&&item.guessName==1) {
                    myImageNameList.add(item.artist + " - " + item.name)
                }else if(item.guessArtist==1){
                    myImageNameList.add(item.artist + " - " + "?")
                }else if(item.guessName==1){
                    myImageNameList.add("?"+ " - " + item.name)
                }else{
                    myImageNameList.add("?"+ " - " + "?")
                }
                imagesIndex.add(item.id)
            }
        }

        for (i in 0..myImageNameList.size-1) {
            val imageModel = MyModel()
            imageModel.setNames((myImageNameList[i]))
            imageModel.setImage_drawables(myImageList[imagesIndex[i]])
            list.add(imageModel)
        }

        return list
    }

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
