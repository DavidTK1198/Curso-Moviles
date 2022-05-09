package com.example.myjobapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myjobapp.model.Company
import kotlinx.android.synthetic.main.activity_company.*
import kotlinx.android.synthetic.main.item_company.view.*

class CompanyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_company)
        val company = intent.getSerializableExtra("Company") as Company

        name_com.text = company.name
        time_com.text = company.time
        descom.text = company.descripcion
        imagen.setImageResource(company.imagen)

    }
}