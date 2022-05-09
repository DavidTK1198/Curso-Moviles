package com.example.myjobapp
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.myjobapp.model.JobApplication
import kotlinx.android.synthetic.main.item_application.view.*
import kotlinx.android.synthetic.main.item_company.view.*
import kotlinx.android.synthetic.main.item_company.view.descrip
import kotlinx.android.synthetic.main.item_company.view.imageView
import kotlinx.android.synthetic.main.item_company.view.nameA
import model.*

class ApplicationAdapter (private val mContext: Context, private val listaApplied: List<JobApplication>) : ArrayAdapter<JobApplication>(mContext, 0, listaApplied){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layout = LayoutInflater.from(mContext).inflate(R.layout.item_application, parent, false)

        val application = listaApplied[position]

        layout.nameA.text = application.company.name
        layout.descrip.text = application.company.descripcion
        layout.imageView.setImageResource(application.company.imagen)
        layout.userA.text="Request By ${application.user.username}"
        return layout
    }
}