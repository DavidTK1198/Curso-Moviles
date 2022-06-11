package com.matricula.sqllite.util

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.matricula.sqllite.R

open class FragmentUtils : Fragment(){
    fun changeFragment(fragment: Fragment? = null, fragmentUtils: FragmentUtils? = null){
        if (fragment != null)
            parentFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
        else
            parentFragmentManager.beginTransaction().replace(R.id.fragment_container, fragmentUtils!!).commit()
    }
    fun setToolbarTitle(title:String){
        (activity as AppCompatActivity).supportActionBar?.title = title
    }
}