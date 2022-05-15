package com.example.uiexamples

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class EditPersona : AppCompatActivity() {
    var personas: Personas = Personas.instance
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_persona)
        val user = intent.extras?.get("editar") as Persona
        val edit = findViewById<Button>(R.id.edit)
        val back = findViewById<Button>(R.id.back)
        var name = findViewById<TextView>(R.id.nameE)
        var username=findViewById<TextView>(R.id.usernameE)
        var password=findViewById<TextView>(R.id.passwordE)
        var logo=findViewById<ImageView>(R.id.imageView2)
        var pos =intent.extras?.get("pos") as Int
         name.text=user.nombre
        username.text=user.user
        password.text=user.password
        logo.setImageResource(user.foto)



        edit.setOnClickListener{
            user.nombre=name.text.toString()
            user.user=username.text.toString()
            user.password=password.text.toString()
            personas.editPerson(user,pos)
            Toast.makeText(this,"Registro modificado", Toast.LENGTH_SHORT).show()
        }

        back.setOnClickListener{
            val crud = Intent(this, CrudPersonas::class.java)
            startActivity(crud)

        }

    }

}