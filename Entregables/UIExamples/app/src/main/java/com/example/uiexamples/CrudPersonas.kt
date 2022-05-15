package com.example.uiexamples
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import java.util.*
import android.content.Intent
import android.view.Menu
import android.widget.Button
import kotlin.collections.ArrayList

class CrudPersonas : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    var personas: Personas = Personas.instance

    lateinit var lista:RecyclerView
    lateinit var adaptador:RecyclerView_Adapter
    lateinit var persona: Persona
    var archived = ArrayList<Persona>()
    var position: Int = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crud_personas)

        val searchIcon = findViewById<ImageView>(R.id.search_mag_icon)
        searchIcon.setColorFilter(Color.BLACK)

        val intent = Intent(this, EditPersona::class.java)
        val cancelIcon = findViewById<ImageView>(R.id.search_close_btn)
        cancelIcon.setColorFilter(Color.BLACK)


        val textView = findViewById<TextView>(R.id.search_src_text)
        textView.setTextColor(Color.BLACK)

        lista = findViewById(R.id.lista)
        lista.layoutManager = LinearLayoutManager(lista.context)
        lista.setHasFixedSize(true)

        findViewById<SearchView>(R.id.person_search).setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adaptador.filter.filter(newText)
                return false
            }
        })


        getListOfPersons()

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.START or ItemTouchHelper.END, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
                    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                        val fromPosition: Int = viewHolder.adapterPosition
                        val toPosition: Int = target.adapterPosition


                        Collections.swap(personas.getPersonas(), fromPosition, toPosition)

                        lista.adapter?.notifyItemMoved(fromPosition, toPosition)

                        return false
                    }

                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                        position = viewHolder.adapterPosition

                        if(direction == ItemTouchHelper.LEFT){
                            persona = Persona(personas.getPersonas()[position].user, personas.getPersonas()[position].password, personas.getPersonas()[position].nombre, personas.getPersonas()[position].foto)
                            personas.deletePerson(position)
                            lista.adapter?.notifyItemRemoved(position)

                            Snackbar.make(lista, persona.nombre + "Se eliminaría...", Snackbar.LENGTH_LONG).setAction("Undo") {
                                personas.getPersonas().add(position, persona)
                                lista.adapter?.notifyItemInserted(position)
                            }.show()
                            adaptador = RecyclerView_Adapter(personas.getPersonas())
                            lista.adapter = adaptador
                        }else{
                            persona = Persona(personas.getPersonas()[position].user, personas.getPersonas()[position].password, personas.getPersonas()[position].nombre, personas.getPersonas()[position].foto)
                            Snackbar.make(lista, persona.nombre + "Se editaría...", Snackbar.LENGTH_LONG).setAction("Undo") {
                            }.show()
                            intent.putExtra("editar",persona as java.io.Serializable)
                            intent.putExtra("pos",position)
                            startActivity(intent)
                        }
                    }

                    override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {

                        RecyclerViewSwipeDecorator.Builder(this@CrudPersonas, c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                            .addSwipeLeftBackgroundColor(ContextCompat.getColor(this@CrudPersonas, R.color.red))
                            .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete_24)
                            .addSwipeRightBackgroundColor(ContextCompat.getColor(this@CrudPersonas, R.color.green))
                            .addSwipeRightActionIcon(R.drawable.ic_baseline_edit_24)
                            .create()
                            .decorate()
                        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    }

                }



        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(lista)



        val add: FloatingActionButton = findViewById(R.id.add)
        add.setOnClickListener { view ->
            val addp = Intent(this, AddPersona::class.java)
            startActivity(addp)
        }


    }



    private fun getListOfPersons() {
        val Npersonas = ArrayList<Persona>()
        for (p in personas.getPersonas()) {
            Npersonas.add(p)
        }
        adaptador = RecyclerView_Adapter(Npersonas)
        lista.adapter = adaptador
    }
}