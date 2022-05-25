package com.example.uiexamples


import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.uiexamples.ui.gallery.GalleryFragment
import com.example.uiexamples.ui.home.HomeFragment
import com.example.uiexamples.ui.slideshow.SlideshowFragment
import com.google.android.material.navigation.NavigationView


class MenuExample : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener  {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var homeFragment: HomeFragment
    private lateinit var user:User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_example)

        val bundle = intent.extras
        val msg = bundle!!.getString("msg")
        val l =  bundle.getSerializable("Login") as User
        user=l

        if (msg != null) {
            Toast.makeText(this, "$msg ${l.nombre} ${l.password}", Toast.LENGTH_SHORT).show()
        }

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.logout, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navView.setNavigationItemSelectedListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_example, menu)
        val logo= findViewById<ImageView>(R.id.logo)
        logo.setImageResource(user?.foto!!)
        logo.getLayoutParams().width = 200;
       var navigationView = findViewById<NavigationView>(R.id.nav_view);
        var  nav_Menu = navigationView.getMenu();
        if(user.rol=="Standard")
        nav_Menu.findItem(R.id.nav_gallery).setVisible(false)
        else
            nav_Menu.findItem(R.id.nav_personas).setVisible(false)

        return true
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId){
            R.id.logout -> {
                homeFragment = HomeFragment()
                supportFragmentManager
                    .beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .replace(R.id.nav_host_fragment, homeFragment)
                    .addToBackStack(null)
                    .commit()

            }
            R.id.nav_personas -> {
                val i = Intent(this, ActivityJopApp::class.java)
                i.putExtra("user", user)
                startActivity(i)
                finish()

            }

            R.id.nav_gallery -> {
                val i = Intent(this, CrudPersonas::class.java)
                i.putExtra("user", user)
                startActivity(i)
                finish()
            }

            R.id.logoutm -> {
                val i = Intent(this, Login::class.java)

                startActivity(i)
                finish()
            }
        }
        return true
    }
    override fun onBackPressed() {
        val fragments = supportFragmentManager.backStackEntryCount
        if (fragments == 1) {
            finish()
            return
        }
        super.onBackPressed()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}