package com.example.myjobapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.content.Intent
import android.widget.Toast
import model.*

class LoginActivity : AppCompatActivity() {
    var user=User()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val username = findViewById<EditText>(R.id.editTextEmailAddress)
        val password = findViewById<EditText>(R.id.editTextPassword)
        val send = findViewById<Button>(R.id.buttonLogin)
        send.setOnClickListener {
            var u  = username.text.toString()
            var p = password.text.toString()
            val userskills=user.skills
            userskills.add("Java")
            userskills.add("C++")
            userskills.add("Kotlin")
            userskills.add("C")
            userskills.add("Kotlin")
            val intent = Intent(this, ActivityJopApp::class.java)
            if(login(u,p) ){
                var us = user
                    intent.putExtra("job_form",us as java.io.Serializable)
                    startActivity(intent);
            }else Toast.makeText(this,"Datos Incorrectos",Toast.LENGTH_SHORT).show()
        }

    }


    fun login(Username:String,Password:String):Boolean{
        if (user.username==Username && user.password==Password){
            return true
        }
        return false
    }

}