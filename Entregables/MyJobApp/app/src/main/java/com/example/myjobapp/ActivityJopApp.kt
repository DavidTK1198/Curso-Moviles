package com.example.myjobapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
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
        val address2 = findViewById<TextView>(R.id.address2Input)
        val city = findViewById<TextView>(R.id.cityInput)
        val state = findViewById<TextView>(R.id.stateInput)
        val code = findViewById<TextView>(R.id.zipCodeInput)
        val country = findViewById<TextView>(R.id.country)
        val area = findViewById<TextView>(R.id.areaCode)
        val phone = findViewById<TextView>(R.id.phoneNumber)
        val position = findViewById<TextView>(R.id.positionInput)
        val date = findViewById<TextView>(R.id.Dateinput)
        val email = findViewById<TextView>(R.id.emailid)
        val rg = findViewById<RadioGroup>(R.id.RadioGroup)

        nameInput.text = us.firstname
        lastNameInput.text = us.lastname
        addres1.text = us.address1
        address2.text = us.address2
        city.text = us.city
        state.text = us.state
        code.text = us.code
        country.text = us.country
        area.text = us.area
        phone.text = us.phoneNumber
        position.text = us.position
        date.text = us.startDate
        email.text = us.email

        back.setOnClickListener {
            val login = Intent(this, LoginActivity::class.java)
            startActivity(login)
        }

        skill.setOnClickListener {
            val skillmenu = Intent(this, SkillActivity::class.java)
            intent.putExtra("job_form", us as java.io.Serializable)
            startActivity(skillmenu)
        }

        rg.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener{group, checkedId ->
            val radio:RadioButton = group.findViewById<RadioButton>(checkedId)
            Log.e("selectedtext-->",radio.text.toString())})
    }
}