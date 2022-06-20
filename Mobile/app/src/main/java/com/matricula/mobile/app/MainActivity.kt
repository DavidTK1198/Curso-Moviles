package com.matricula.mobile.app
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.google.gson.Gson
import com.matricula.mobile.*
import com.matricula.mobile.apiService.LoginService
import com.matricula.mobile.app.ui.*
import com.matricula.mobile.models.Usuario
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private  lateinit var usuario: Usuario
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val us = intent.extras?.get("user") as Usuario
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        if(us!=null){
            usuario=us
        }else{
            usuario=Usuario()
            salir()
        }
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

            R.id.nav_item_historial -> {
                setToolbarTitle("Historial")
                val gson = Gson()
                var bundle =  Bundle()
                val historial=InscripcionesFragment()
                val json=gson.toJson(usuario)
                bundle.putString("us", json)
                historial.arguments=bundle
                changeFragment(historial)
            }
            R.id.nav_item_ciclos -> {
                setToolbarTitle("Ciclos")
                changeFragment(CiclosFragment())
            }
            R.id.nav_item_logout -> {
            logout()
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
        val headerView: View = navigationView.getHeaderView(0)
        val nombre= headerView.findViewById<TextView>(R.id.nav_header_nombre)
        nombre.setText(usuario.nombre)
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
        menu.findItem(R.id.nav_item_historial).setVisible(false)
    }

    private fun Matriculador(menu: Menu){
        menu.findItem(R.id.nav_item_notas).setVisible(false)
        menu.findItem(R.id.nav_item_profesores).setVisible(false)
        menu.findItem(R.id.nav_item_historial).setVisible(false)
        menu.findItem(R.id.nav_item_usuarios).setVisible(false)
        menu.findItem(R.id.nav_item_carreras).setVisible(false)
        menu.findItem(R.id.nav_item_ciclos).setVisible(false)
        menu.findItem(R.id.nav_item_oferta).setVisible(false)
        menu.findItem(R.id.nav_item_ciclos).setVisible(false)
    }

    private fun Alumno(menu: Menu){
        menu.findItem(R.id.nav_item_notas).setVisible(false)
        menu.findItem(R.id.nav_item_profesores).setVisible(false)
        menu.findItem(R.id.nav_item_alumnos).setVisible(false)
        menu.findItem(R.id.nav_item_usuarios).setVisible(false)
        menu.findItem(R.id.nav_item_carreras).setVisible(false)
        menu.findItem(R.id.nav_item_ciclos).setVisible(false)
        menu.findItem(R.id.nav_item_oferta).setVisible(false)
        menu.findItem(R.id.nav_item_ciclos).setVisible(false)
    }
    private fun Profesor(menu: Menu){
        menu.findItem(R.id.nav_item_historial).setVisible(false)
        menu.findItem(R.id.nav_item_profesores).setVisible(false)
        menu.findItem(R.id.nav_item_alumnos).setVisible(false)
        menu.findItem(R.id.nav_item_usuarios).setVisible(false)
        menu.findItem(R.id.nav_item_carreras).setVisible(false)
        menu.findItem(R.id.nav_item_ciclos).setVisible(false)
        menu.findItem(R.id.nav_item_oferta).setVisible(false)
        menu.findItem(R.id.nav_item_ciclos).setVisible(false)
    }

    private  fun logout(){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val call = LoginService.Companion.getInstance().logout()
                if (call.isSuccessful) {
                    salir()
                } else {
                }
            } catch (e: SocketTimeoutException) {
                Log.d("xd", "mamado")
            }
        }
    }

    private fun salir(){
        val intent = Intent(this, Login::class.java)
        startActivity(intent)
        finish()
    }

}

