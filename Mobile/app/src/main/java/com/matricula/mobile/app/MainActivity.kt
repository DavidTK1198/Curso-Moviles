package com.matricula.mobile.app
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.Nullable
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.material.navigation.NavigationView
import com.matricula.mobile.*
import com.matricula.mobile.app.ui.AlumnosFragment
import com.matricula.mobile.app.ui.CarrerasFragment
import com.matricula.mobile.models.Alumno
import com.matricula.mobile.models.Carrera
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close)
        toggle.isDrawerIndicatorEnabled = true
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        nav_menu.setNavigationItemSelectedListener(this)
        //changeFragment(HomeFragment())
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        drawerLayout.closeDrawer(GravityCompat.START)
        when (item.itemId) {
            R.id.nav_item_home -> {
                setToolbarTitle("Home")

            }
            R.id.nav_item_carreras -> {
                setToolbarTitle("Carreras")
                changeFragment(CarrerasFragment())
            }
            R.id.nav_item_alumnos -> {
                setToolbarTitle("Alumnos")
                changeFragment(AlumnosFragment())
            }

            R.id.nav_item_logout -> {
                val intent = Intent(this, Login::class.java)
                startActivity(intent)
                finish()
            }
        }
        return true
    }

    fun setToolbarTitle(title: String) {
        supportActionBar?.title = title
    }

    fun changeFragment(fragment: Fragment) {

        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        var navigationView = findViewById<NavigationView>(R.id.nav_menu)
        var nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.nav_item_carreras).setVisible(false)
        return true
    }

    private fun Administrador(){

    }

    private fun Matriculador(){

    }

    private fun Alumno(){

    }
    private fun Profesor(){

    }

}

