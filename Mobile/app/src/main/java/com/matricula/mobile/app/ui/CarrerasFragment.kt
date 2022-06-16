package com.matricula.mobile.app.ui
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.matricula.mobile.R
import com.matricula.mobile.adapter.CarreraAdapter
import com.matricula.mobile.apiService.CarreraService
import com.matricula.mobile.models.Carrera
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class CarrerasFragment: FragmentUtils() {

    lateinit var recyclerViewElement: RecyclerView
    lateinit var adaptador: CarreraAdapter
    private lateinit var addsBtn: FloatingActionButton
    private lateinit var  listaCarrera:ArrayList<Carrera>
    var position: Int = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_carreras, container, false)

        recyclerViewElement = view.findViewById(R.id.mRecycler)
        recyclerViewElement.layoutManager = LinearLayoutManager(recyclerViewElement.context)
        recyclerViewElement.setHasFixedSize(true)

       view.findViewById<SearchView>(R.id.carrera_search)
          .setOnQueryTextListener(object : SearchView.OnQueryTextListener {
               override fun onQueryTextSubmit(query: String?): Boolean {
                   return false
               }

              override fun onQueryTextChange(newText: String?): Boolean {
                 adaptador.filter.filter(newText)

                   return false
              }
           })
            addsBtn= view.findViewById(R.id.addingBtn)
        addsBtn.setOnClickListener { view ->
            setToolbarTitle("Crear Carrera")
            changeFragment(CrearCarreraFragment())
        }
            getListOfCarreras()
        return view;
    }
    private fun  getListOfCarreras() {
        CoroutineScope(Dispatchers.IO).launch {
            val call = CarreraService.getInstance().obtenerCarreras()
            val nCarreras = call.body()
            if (call.isSuccessful) {
                withContext(Dispatchers.Main) {
                    listaCarrera = (nCarreras as ArrayList<Carrera>?)!!
                    refresh()
                }
            } else {

            }
        }
    }
    private  fun refresh(){
        adaptador = CarreraAdapter(this.activity!!, listaCarrera)
        recyclerViewElement.adapter = adaptador
    }

}