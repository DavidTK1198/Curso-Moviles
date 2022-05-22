package com.example.uiexamples

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast

class Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        var users: Users = Users.instance

        val add = findViewById<Button>(R.id.register)
        val terms = findViewById<CheckBox>(R.id.terms)
        add.setOnClickListener{
            var name = findViewById<TextView>(R.id.nameR).text.toString()
            var username=findViewById<TextView>(R.id.usernameR).text.toString()
            var password=findViewById<TextView>(R.id.passwordR).text.toString()
            if(name=="" || username=="" || password==""){
                Toast.makeText(this,"Por favor rellene todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(!terms.isChecked){
                Toast.makeText(this,"Por favor acepte los terminos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            //users.addPersona(User(username,password,name, R.drawable.ic_launcher_background))
            Toast.makeText(this,"Registro agregado", Toast.LENGTH_SHORT).show()
            Thread.sleep(1000)
            val login = Intent(this, Login::class.java)
            startActivity(login)

        }

    }
}