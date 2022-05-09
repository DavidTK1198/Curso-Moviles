package com.example.myjobapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity
import model.*


class ActivityJopApp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jop_app)
        val us = intent.extras?.get("job_form") as User
        val back = findViewById<Button>(R.id.btnBack)
        val skill = findViewById<Button>(R.id.Skill)
        val nameInput = findViewById<TextView>(R.id.nameInput)
        val lastNameInput = findViewById<TextView>(R.id.lastNameInput)
        val addres1 = findViewById<TextView>(R.id.address1Input)
        val phone = findViewById<TextView>(R.id.phoneNumber)
        val position = findViewById<TextView>(R.id.positionInput)
        val email = findViewById<TextView>(R.id.emailid)
        val rg = findViewById<RadioGroup>(R.id.RadioGroup)
        val otro = findViewById<RadioButton>(R.id.otro)
        val M = findViewById<RadioButton>(R.id.M)
        val H = findViewById<RadioButton>(R.id.H)
        val availability = findViewById<ToggleButton>(R.id.toggleButton)
        availability.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                availability.setTextOn("Yes");
            } else {
                availability.setTextOff("No");
            }
        }


        when {
            us.gender == "M" -> rg.check(M.id)
            us.gender == "H" -> rg.check(H.id)
            else -> rg.check(otro.id)
        }

        nameInput.text = us.firstname
        lastNameInput.text = us.lastname
        addres1.text = us.address1
        phone.text = us.phoneNumber
        position.text = us.position
        email.text = us.email
        back.setOnClickListener {
            val login = Intent(this, LoginActivity::class.java)
            us.firstname = nameInput.text.toString()
            us.lastname = lastNameInput.text.toString()
            us.address1 = addres1.text.toString()
            us.phoneNumber = phone.text.toString()
            us.position = position.text.toString()
            us.email = email.text.toString()
            intent.putExtra("User", us as java.io.Serializable)
            startActivity(login)
        }

        skill.setOnClickListener {
            val skillmenu = Intent(this, SkillActivity::class.java)
            intent.putExtra("job_form", us as java.io.Serializable)
            startActivity(skillmenu)
        }

        rg.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
            val radio: RadioButton = group.findViewById<RadioButton>(checkedId)
            Log.e("selectedtext-->", radio.text.toString())
        })
    }
}