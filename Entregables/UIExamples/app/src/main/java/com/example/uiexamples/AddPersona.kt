package com.example.uiexamples

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class AddPersona : AppCompatActivity() {
    var personas: Personas = Personas.instance
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_persona)

        val add = findViewById<Button>(R.id.edit)
        val back = findViewById<Button>(R.id.back)

        add.setOnClickListener{
            var name = findViewById<TextView>(R.id.name).text.toString()
            var username=findViewById<TextView>(R.id.usernameA).text.toString()
            var password=findViewById<TextView>(R.id.passwordR).text.toString()
            if(name=="" || username=="" || password==""){
                Toast.makeText(this,"Por favor rellene todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
           personas.addPersona(Persona(username,password,name, R.drawable.ic_launcher_background))
            Toast.makeText(this,"Registro agregado", Toast.LENGTH_SHORT).show()

        }

        back.setOnClickListener{
            val crud = Intent(this, CrudPersonas::class.java)
            startActivity(crud)

        }

    }
}