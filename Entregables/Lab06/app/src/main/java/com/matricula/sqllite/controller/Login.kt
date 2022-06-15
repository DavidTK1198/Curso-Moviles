package com.matricula.sqllite.controller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.matricula.sqllite.R
import com.matricula.sqllite.logicanegocio.login

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (this.supportActionBar != null)
            this.supportActionBar!!.hide()
        var et_user_name = findViewById(R.id.et_user_name) as EditText
        var et_password = findViewById(R.id.et_password) as EditText
        var btn_submit = findViewById(R.id.btn_submit) as Button
        btn_submit.setOnClickListener {
            val user_name = et_user_name.text
            val password = et_password.text
            var islogged=login(user_name.toString(),password.toString())
            if(!islogged){
                Toast.makeText(applicationContext,"Usuario no encontrado", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            finish()
        }
    }
}