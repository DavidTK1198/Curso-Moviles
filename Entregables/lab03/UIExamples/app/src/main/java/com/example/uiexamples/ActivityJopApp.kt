package com.example.uiexamples

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class ActivityJopApp : AppCompatActivity() {

    lateinit var user: User
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jop_app)
        user = intent.extras?.get("user") as User
        val back = findViewById<Button>(R.id.backJob)
        val nameInput = findViewById<TextView>(R.id.nameInput)
        val addres1 = findViewById<TextView>(R.id.address)
        val city= findViewById<TextView>(R.id.city)
        val code= findViewById<TextView>(R.id.zipCodeInput)
        val country= findViewById<TextView>(R.id.country)
        val phone= findViewById<TextView>(R.id.phoneNumber)
        val position= findViewById<TextView>(R.id.positionInput)
        val email=findViewById<TextView>(R.id.emailid)
        val logo=findViewById<ImageView>(R.id.userlogo)
        logo.setImageResource(user?.foto!!)
        nameInput.setEnabled(false)
        addres1.setEnabled(false)
        city.setEnabled(false)
        code.setEnabled(false)
        country.setEnabled(false)
        phone.setEnabled(false)
        position.setEnabled(false)
        email.setEnabled(false)
        nameInput.text = user.nombre
        addres1.text =user.address
        city.text =user.city
        code.text =user.code
        country.text =user.country
        phone.text =user.phoneNumber
        position.text =user.position
        email.text=user.email

        back.setOnClickListener {
            val i = Intent(this, MenuExample::class.java)
            i.putExtra("msg", "MENSAJE DE JobApp al Menú")
            i.putExtra("Login", user)
            startActivity(i)
            finish()
        }
    }
}