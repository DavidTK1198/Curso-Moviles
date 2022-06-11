package com.matricula.sqllite.controller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.matricula.sqllite.R

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

            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            finish()
        }
    }
}