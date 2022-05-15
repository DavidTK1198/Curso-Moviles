package com.example.uiexamples

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginExample : AppCompatActivity() {

    var personas: Personas = Personas.instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_example)
        // get reference to all views


        var et_user_name = findViewById(R.id.usernameE) as EditText
        var et_password = findViewById(R.id.passl) as EditText
        var btn_submit = findViewById(R.id.buttonLogin) as Button
        val register = findViewById<Button>(R.id.singup)

        register.setOnClickListener{
            val reg = Intent(this, Register::class.java)
            startActivity(reg)

        }
        // set on-click listener
        btn_submit.setOnClickListener {
            val user_name = et_user_name.text;
            val password = et_password.text;
            //Toast.makeText(this@LoginExample, user_name, Toast.LENGTH_LONG).show()
            if(personas.login(user_name.toString(), password.toString())){
                val bundle = Bundle()
                val Login = personas.loginP(user_name.toString(), password.toString())
                val i = Intent(this, MenuExample::class.java)
                i.putExtra("msg", "MENSAJE DE Login al Menú")
                i.putExtra("Login", Login)
//            i.putExtra("passw", password.toString())
                // start your next activity
                startActivity(i)
                // your code to validate the user_name and password combination
                // and verify the same
            }else{
                Toast.makeText(this, "El usuario no se encuentra registrado", Toast.LENGTH_SHORT).show()
            }

        }

    }
}