package com.example.uiexamples
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import java.util.*
import android.content.Intent
import kotlin.collections.ArrayList

class CrudPersonas : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    var users: Users = Users.instance

    lateinit var lista:RecyclerView
    lateinit var adaptador:RecyclerView_Adapter
    lateinit var user: User
    var position: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crud_personas)
        user = intent.extras?.get("user") as User
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


                        Collections.swap(users.getPersonas(), fromPosition, toPosition)

                        lista.adapter?.notifyItemMoved(fromPosition, toPosition)

                        return false
                    }

                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                        position = viewHolder.adapterPosition

                        if(direction == ItemTouchHelper.LEFT){
                            user = User(users.getPersonas()[position].user, users.getPersonas()[position].password, users.getPersonas()[position].nombre, users.getPersonas()[position].foto,users.getPersonas()[position].rol,"","","","","","","")
                            users.deletePerson(position)
                            lista.adapter?.notifyItemRemoved(position)

                            Snackbar.make(lista, user.nombre + "Se eliminaría...", Snackbar.LENGTH_LONG).setAction("Undo") {
                                users.getPersonas().add(position, user)
                                lista.adapter?.notifyItemInserted(position)
                            }.show()
                            adaptador = RecyclerView_Adapter(users.getPersonas())
                            lista.adapter = adaptador
                        }else{
//                            user = User(users.getPersonas()[position].user, users.getPersonas()[position].password, users.getPersonas()[position].nombre, users.getPersonas()[position].foto,users.getPersonas()[position].rol,"","","","","","","")
//                            Snackbar.make(lista, user.nombre + "Se editaría...", Snackbar.LENGTH_LONG).setAction("Undo") {
//                            }.show()
//                            intent.putExtra("editar",user as java.io.Serializable)
//                            intent.putExtra("pos",position)
//                            startActivity(intent)
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



        val backbutton: FloatingActionButton = findViewById(R.id.addb)
        backbutton.setOnClickListener { view ->
            val addp = Intent(this, MenuExample::class.java)
            addp.putExtra("msg", "MENSAJE DE CRUD al Menú")
            addp.putExtra("Login", user)
            startActivity(addp)
            finish()
        }

        val add: FloatingActionButton = findViewById(R.id.addb2)
        add.setOnClickListener { view ->
            val addp = Intent(this, AddPersona::class.java)
            addp.putExtra("msg", "MENSAJE DE CRUD al Menú")
            addp.putExtra("user", user)
            startActivity(addp)
            finish()
        }


    }

    private fun getListOfPersons() {
        val Npersonas = ArrayList<User>()
        for (p in users.getPersonas()) {
            if(p.rol!="Administrador")
            Npersonas.add(p)
        }
        adaptador = RecyclerView_Adapter(Npersonas)
        lista.adapter = adaptador
    }
}