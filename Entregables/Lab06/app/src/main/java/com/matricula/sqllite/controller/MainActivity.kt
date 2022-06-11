package com.matricula.sqllite.controller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.example.peopleapp.EstudiantesFragment
import com.google.android.material.navigation.NavigationView
import com.matricula.sqllite.R
import com.matricula.sqllite.accesodatos.DatabaseHelper
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    internal var dbHelper = DatabaseHelper(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close)
        toggle.isDrawerIndicatorEnabled = true
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        nav_menu.setNavigationItemSelectedListener(this)

        val navigationView = findViewById<NavigationView>(R.id.nav_menu)
        val header = navigationView?.getHeaderView(0)


    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        drawerLayout.closeDrawer(GravityCompat.START)
        when (item.itemId) {
            R.id.nav_item_estudiantes -> {
                setToolbarTitle("Estudiantes")
                changeFragment(EstudiantesFragment(dbHelper))
            }

            R.id.nav_item_logout -> {
                val intent = Intent(this, Login::class.java)
                startActivity(intent)
                finish()
            }
        }
        return true
    }

    private fun setToolbarTitle(title: String) {
        supportActionBar?.title = title
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()


    }
}
