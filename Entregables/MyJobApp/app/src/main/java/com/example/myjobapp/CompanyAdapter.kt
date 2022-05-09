package com.example.myjobapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.myjobapp.model.Company
import kotlinx.android.synthetic.main.item_company.view.*

class CompanyAdapter(private val mContext: Context, private val listaCompanies: List<Company>) : ArrayAdapter<Company>(mContext, 0, listaCompanies) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layout = LayoutInflater.from(mContext).inflate(R.layout.item_company, parent, false)

        val company = listaCompanies[position]

        layout.nameA.text = company.name
        layout.descrip.text = company.time
        layout.imageView.setImageResource(company.imagen)

        return layout
    }

}