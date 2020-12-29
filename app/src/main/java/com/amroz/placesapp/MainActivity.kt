package com.amroz.placesapp



import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout

import com.google.android.material.navigation.NavigationView




class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)


        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            0,
            0
        )


        toggle.isDrawerIndicatorEnabled=true
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_profile -> {


                sendcontact("profile")


            }
            R.id.nav_messages -> {

                sendcontact("messages")

            }
            R.id.nav_friends -> {

                sendcontact("Frinds")

            }
            R.id.nav_update -> {

                sendcontact("update")

            }
            R.id.nav_logout -> {



            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    fun sendcontact(data:String){
        val currentFragment =
            supportFragmentManager.findFragmentById(R.id.container)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, contactFragment.newInstance(data))
            .commit()}

}