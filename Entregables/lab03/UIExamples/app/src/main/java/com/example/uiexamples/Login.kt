package com.example.uiexamples

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Login : AppCompatActivity() {

    var users: Users = Users.instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_example)
        // get reference to all views


        var et_user_name = findViewById(R.id.usernameE) as EditText
        var et_password = findViewById(R.id.passl) as EditText
        var btn_submit = findViewById(R.id.buttonLogin) as Button
        val register = findViewById<Button>(R.id.singup)
        val forgot=findViewById<TextView>(R.id.forgot)
        forgot.setOnClickListener{
            val intent = Intent(this, EditPersona::class.java)
            startActivity(intent)
            finish()
        }

        register.setOnClickListener{
            val reg = Intent(this, Register::class.java)
            startActivity(reg)

        }
        // set on-click listener
        btn_submit.setOnClickListener {
            val user_name = et_user_name.text;
            val password = et_password.text;
            //Toast.makeText(this@LoginExample, user_name, Toast.LENGTH_LONG).show()
            if(users.login(user_name.toString(), password.toString())){
                val bundle = Bundle()
                val Login = users.loginP(user_name.toString(), password.toString())
                val i = Intent(this, MenuExample::class.java)
                i.putExtra("msg", "MENSAJE DE Login al Menú")
                i.putExtra("Login", Login)
                startActivity(i)
                finish()
            }else{
                Toast.makeText(this, "The user is not registered", Toast.LENGTH_SHORT).show()
            }

        }

    }
}