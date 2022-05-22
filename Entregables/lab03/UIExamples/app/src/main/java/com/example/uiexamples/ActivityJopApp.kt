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
        val back = findViewById<Button>(R.id.register)
        val nameInput = findViewById<TextView>(R.id.nameInput)
        val addres1 = findViewById<TextView>(R.id.address1Input)
        val city= findViewById<TextView>(R.id.cityInput)
        val state = findViewById<TextView>(R.id.stateInput)
        val code= findViewById<TextView>(R.id.zipCodeInput)
        val country= findViewById<TextView>(R.id.country)
        val phone= findViewById<TextView>(R.id.phoneNumber)
        val position= findViewById<TextView>(R.id.positionInput)
        val date= findViewById<TextView>(R.id.Dateinput)
        val email=findViewById<TextView>(R.id.emailid)
        nameInput.setEnabled(false)
        addres1.setEnabled(false)
        city.setEnabled(false)
        state.setEnabled(false)
        code.setEnabled(false)
        country.setEnabled(false)
        phone.setEnabled(false)
        position.setEnabled(false)
        date.setEnabled(false)
        email.setEnabled(false)
        nameInput.text = user.nombre
        addres1.text =user.address
        city.text =user.city
        state.text =user.state
        code.text =user.code
        country.text =user.country
        phone.text =user.phoneNumber
        position.text =user.position
        date.text =user.startDate
        email.text=user.email

        back.setOnClickListener {
            val intent= Intent(this, MenuExample::class.java)
            startActivity(intent)
            finish()
        }
    }
}