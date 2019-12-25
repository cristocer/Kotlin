package com.example.myapplication

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
import android.content.Intent
import android.widget.Button
import com.example.myapplication.Models.Song
import com.example.myapplication.DBHelper.DBHelper
import com.example.myapplication.Models.MyModel

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener  {

    lateinit var db : DBHelper
    var songsList:List<Song> = ArrayList<Song>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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

        /*db= DBHelper(this)
        songsList=db.allSongs
        println("penis")
        for(item in songsList ){
            println(item.name)
        }*/
        //val jsonString = loadJson(this,songName)
        //println(jsonString)

        //buttons
        val mycollectionButton: Button = findViewById(R.id.mycol_butt)
        // set on-click listener
        mycollectionButton.setOnClickListener {
            // your code to perform when the user clicks on the button
            //Toast.makeText(this@MainActivity, "You clicked me.", Toast.LENGTH_SHORT).show()

            val intentToStartDetailActivity = Intent(this, Mycollection::class.java)
            startActivity(intentToStartDetailActivity)
        }

        val playNowButton: Button = findViewById(R.id.start_butt)
        // set on-click listener
        playNowButton.setOnClickListener {
            // your code to perform when the user clicks on the button
            var intent = Intent(applicationContext, GuessActivity::class.java)
            startActivity(intent)
        }



    }

    private fun populateList(): ArrayList<MyModel> {
        val list = ArrayList<MyModel>()
        val myImageList = intArrayOf(R.mipmap.neww)
        val myImageNameList = arrayOf("The Latest Hits are out! Update the app for a new Adventure ! Conquer The Music World !")//(R.string.alfa, R.string.ferrari)

        for (i in 0..0) {
            val imageModel = MyModel()
            imageModel.setNames((myImageNameList[i]))
            imageModel.setImage_drawables(myImageList[i])
            list.add(imageModel)
        }
        return list
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
                val intentToStartDetailActivity = Intent(this, GuessActivity::class.java)
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
