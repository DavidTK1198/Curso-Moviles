package com.example.uiexamples

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class EditPersona : AppCompatActivity() {
    var users: Users = Users.instance
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_persona)
        val edit = findViewById<Button>(R.id.edit)
        edit.setOnClickListener{

            var email = findViewById<TextView>(R.id.emailE).text.toString()
            var username=findViewById<TextView>(R.id.nameE).text.toString()
            var password=findViewById<TextView>(R.id.passwordE).text.toString()
            if(username=="" || password==""||email==""){
                Toast.makeText(this,"Please fill all the fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            var us=User(username,password, "", R.drawable.foto02,"","","","","",email,"")
            var pos= users.search(us)
            if(pos==-1){
                Toast.makeText(this,"User not found", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            us=users.getPersonas().get(pos)
            us.password=password
            Toast.makeText(this,"Record modified", Toast.LENGTH_SHORT).show()
            Thread.sleep(1000)
            val login = Intent(this, Login::class.java)
            startActivity(login)
            finish()
        }


    }

}