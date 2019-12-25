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
import com.example.myapplication.Models.MyModel

class Achievements : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_achievements)
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
        var imageModelArrayList: ArrayList<MyModel> = populateList()
        val recyclerView = findViewById<View>(R.id.my_recycler_view) as RecyclerView
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        val mAdapter = MyAdapter(imageModelArrayList)
        mAdapter.setOnItemClickListener(object : MyAdapter.ClickListener {
            override fun onClick(pos: Int, aView: View) {
                if(pos == 0) {
                    var intent = Intent(aView.context, SettingsActivity::class.java)
                    startActivity(intent)
                }
            }
        })
        recyclerView.adapter = mAdapter

    }

    private fun populateList(): ArrayList<MyModel> {
        val list = ArrayList<MyModel>()
        val myImageList = intArrayOf(R.mipmap.neww, R.mipmap.oldd, R.mipmap.i1, R.mipmap.i2, R.mipmap.i3, R.mipmap.i7)
        val myImageNameList = arrayOf("Pop Master!","50 Classic songs completed ;)","Pre Malone Era.","All Legion Albums.","Love is in the Air!","Unstoppable!!!"
            )//(R.string.alfa, R.string.ferrari)

        for (i in 0..5) {
            val imageModel = MyModel()
            imageModel.setNames((myImageNameList[i]))
            imageModel.setImage_drawables(myImageList[i])
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
