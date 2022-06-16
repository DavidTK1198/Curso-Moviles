package com.matricula.mobile.app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.matricula.mobile.R
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.dialog_success.view.*


class SuccessDiaglogFragment : DialogFragment (){
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var root= inflater.inflate(R.layout.dialog_success, container, false)
        root.btn_confirm.setOnClickListener{
            dismiss()
        }
        var mensaje=this.arguments!!.get("mensaje").toString()
        root.messageModal.text = mensaje
        return  root
    }

}