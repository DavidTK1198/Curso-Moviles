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
import com.matricula.mobile.app.ui.*
import com.matricula.mobile.models.Alumno
import com.matricula.mobile.models.Carrera
import com.matricula.mobile.models.Usuario
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private  lateinit var usuario: Usuario
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        usuario=Usuario()
        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close)
        toggle.isDrawerIndicatorEnabled = true
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        nav_menu.setNavigationItemSelectedListener(this)
        changeFragment(InicioFragment())
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        drawerLayout.closeDrawer(GravityCompat.START)
        when (item.itemId) {
            R.id.nav_item_home -> {
                setToolbarTitle("Inicio")
                changeFragment(InicioFragment())

            }
            R.id.nav_item_carreras -> {
                setToolbarTitle("Carreras")
                changeFragment(CarrerasFragment())
            }
            R.id.nav_item_alumnos -> {
                setToolbarTitle("Alumnos")
                changeFragment(AlumnosFragment())
            }

            R.id.nav_item_profesores -> {
                setToolbarTitle("Profesores")
                changeFragment(ProfesoresFragment())
            }

            R.id.nav_item_usuarios -> {
                setToolbarTitle("Usuarios")
                changeFragment(UsuariosFragment())
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
        var nav_Menu = navigationView.getMenu()
        when (usuario.rol) {
            "ADM" -> {Administrador(nav_Menu)}
            "EST" -> {Alumno(nav_Menu)}
            "MAT" -> {Matriculador(nav_Menu)}
            "PROF" -> {Profesor(nav_Menu)}
        }
        return true
    }

    fun usuario(): Usuario {
        return this.usuario
    }

    //Gestionar acceso a secciones
    private fun Administrador(menu: Menu){
        menu.findItem(R.id.nav_item_notas).setVisible(false)
    }

    private fun Matriculador(menu: Menu){
        menu.findItem(R.id.nav_item_notas).setVisible(false)
        menu.findItem(R.id.nav_item_profesores).setVisible(false)
        menu.findItem(R.id.nav_item_historial).setVisible(false)
        menu.findItem(R.id.nav_item_usuarios).setVisible(false)
        menu.findItem(R.id.nav_item_carreras).setVisible(false)
        menu.findItem(R.id.nav_item_ciclos).setVisible(false)
        menu.findItem(R.id.nav_item_oferta).setVisible(false)
    }

    private fun Alumno(menu: Menu){
        menu.findItem(R.id.nav_item_notas).setVisible(false)
        menu.findItem(R.id.nav_item_profesores).setVisible(false)
        menu.findItem(R.id.nav_item_alumnos).setVisible(false)
        menu.findItem(R.id.nav_item_usuarios).setVisible(false)
        menu.findItem(R.id.nav_item_carreras).setVisible(false)
        menu.findItem(R.id.nav_item_ciclos).setVisible(false)
        menu.findItem(R.id.nav_item_oferta).setVisible(false)
    }
    private fun Profesor(menu: Menu){
        menu.findItem(R.id.nav_item_historial).setVisible(false)
        menu.findItem(R.id.nav_item_profesores).setVisible(false)
        menu.findItem(R.id.nav_item_alumnos).setVisible(false)
        menu.findItem(R.id.nav_item_usuarios).setVisible(false)
        menu.findItem(R.id.nav_item_carreras).setVisible(false)
        menu.findItem(R.id.nav_item_ciclos).setVisible(false)
        menu.findItem(R.id.nav_item_oferta).setVisible(false)
    }

}

