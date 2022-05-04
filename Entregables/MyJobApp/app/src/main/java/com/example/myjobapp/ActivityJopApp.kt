package com.example.myjobapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import model.*
import android.widget.Button
import android.widget.TextView

class ActivityJopApp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jop_app)
        val user = intent.extras?.get("job_form") as User
        val back = findViewById<Button>(R.id.btnBack)
        val nameInput = findViewById<TextView>(R.id.nameInput)
        val lastNameInput = findViewById<TextView>(R.id.lastNameInput)
        val addres1 = findViewById<TextView>(R.id.address1Input)
        val address2= findViewById<TextView>(R.id.address2Input)
        val city= findViewById<TextView>(R.id.cityInput)
        val state = findViewById<TextView>(R.id.stateInput)
        val code= findViewById<TextView>(R.id.zipCodeInput)
        val country= findViewById<TextView>(R.id.country)
        val area= findViewById<TextView>(R.id.areaCode)
        val phone= findViewById<TextView>(R.id.phoneNumber)
        val position= findViewById<TextView>(R.id.positionInput)
        val date= findViewById<TextView>(R.id.Dateinput)
        val email=findViewById<TextView>(R.id.emailid)

        nameInput.text = user.firstname
        lastNameInput.text = user.lastname
        addres1.text =user.address1
        address2.text =user.address2
            city.text =user.city
            state.text =user.state
            code.text =user.code
            country.text =user.country
            area.text =user.area
            phone.text =user.phoneNumber
            position.text =user.position
            date.text =user.startDate
        email.text=user.email

            back.setOnClickListener {
            val intent= Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}