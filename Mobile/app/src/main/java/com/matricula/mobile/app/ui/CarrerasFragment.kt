package com.matricula.mobile.app.ui
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.matricula.mobile.CarreraViewModel
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
    private val carreraViewModel: CarreraViewModel by activityViewModels()
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
        carreraViewModel.getCarrerasList()!!.observe(viewLifecycleOwner){carreras->
            listaCarrera= carreras as ArrayList<Carrera>
            refresh()
        }
        carreraViewModel.getListOfCarreras()
        return view;
    }
    private  fun refresh(){
        if(carreraViewModel.check_state()==true){
            adaptador = CarreraAdapter(this.activity!!, listaCarrera)
            recyclerViewElement.adapter = adaptador
        }else{
            //hacer mensaje de error
        }
    }

}
