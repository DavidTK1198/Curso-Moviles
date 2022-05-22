package com.example.uiexamples

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class ActivityJopApp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jop_app)
        val user = intent.extras?.get("user") as User
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
        back.setOnClickListener {
            val intent= Intent(this, Login::class.java)
            startActivity(intent)
        }
    }
}