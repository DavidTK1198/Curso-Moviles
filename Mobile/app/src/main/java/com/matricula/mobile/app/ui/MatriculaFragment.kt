package com.matricula.mobile.app.ui

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.annotation.Nullable
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import com.matricula.mobile.R
import com.matricula.mobile.adapter.CursoAdapter
import com.matricula.mobile.adapter.FragmentAdapter
import com.matricula.mobile.apiService.CursoService
import com.matricula.mobile.viewModels.CursoViewModel
import com.matricula.mobile.models.Curso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.SocketTimeoutException

class MatriculaFragment: FragmentUtils() {

    lateinit var recyclerViewElement: RecyclerView
    lateinit var adaptador: CursoAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_matricula, container, false)
        var viewPager = view.findViewById(R.id.viewPager) as ViewPager
        var tablayout = view.findViewById(R.id.tablayout) as TabLayout
        val fragmentAdapter = FragmentAdapter(activity!!.supportFragmentManager)
        fragmentAdapter.addFragment(MatricularFragment(),"Matricular")
        fragmentAdapter.addFragment(DesmatricularFragment(),"Desmatricular")
        viewPager.adapter = fragmentAdapter
        tablayout.setupWithViewPager(viewPager)
        return view
    }

}