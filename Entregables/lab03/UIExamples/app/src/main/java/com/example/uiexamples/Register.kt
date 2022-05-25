package com.example.uiexamples

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class Register : AppCompatActivity() {
    val hashMap= HashMap<Int,Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        var users: Users = Users.instance
        this.initHashmap()
        val add = findViewById<Button>(R.id.register)
        val username = findViewById<EditText>(R.id.name3)
        val fullname = findViewById<EditText>(R.id.nameInput2)
        val email = findViewById<EditText>(R.id.emailid2)
        val password = findViewById<EditText>(R.id.passr)
        val country = findViewById<EditText>(R.id.country2)
        val city = findViewById<EditText>(R.id.city2)
        val address = findViewById<EditText>(R.id.address2)
        val code = findViewById<EditText>(R.id.zipCodeInput2)
        val phone = findViewById<EditText>(R.id.phoneNumber2)
        val apply = findViewById<EditText>(R.id.positionInput2)
        add.setOnClickListener{
            var name = fullname.text.toString()
            var usern=username.text.toString()
            var pass=password.text.toString()
            var emailu=email.text.toString()
            var countryu=country.text.toString()
            var cityu=  city.text.toString()
            var addressu=address.text.toString()
            var cod=code.text.toString()
            var cel=phone.text.toString()
            var applied=apply.text.toString()

            if(name=="" || usern=="" || pass==""||emailu==""||countryu==""||cityu==""||addressu==""||cod==""
                ||cel==""||applied==""){
                Toast.makeText(this,"Please fill in all the fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            var key=(1..7).random()
            var photo=hashMap.get(key)
            users.addPersona(User(usern,pass,name, photo!!,"Standard",cityu,
                addressu,cod,countryu,emailu,cel,applied))
            Toast.makeText(this,"added log", Toast.LENGTH_SHORT).show()
            Thread.sleep(1000)
            val login = Intent(this, Login::class.java)
            startActivity(login)
            finish()

        }

    }

    private fun initHashmap()
    {
        hashMap.put(1,R.drawable.foto01)
        hashMap.put(2,R.drawable.foto02)
        hashMap.put(3,R.drawable.foto03)
        hashMap.put(4,R.drawable.foto04)
        hashMap.put(5,R.drawable.foto05)
        hashMap.put(6,R.drawable.foto06)
        hashMap.put(7,R.drawable.foto07)
    }}