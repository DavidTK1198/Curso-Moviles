package com.example.myjobapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class SkillActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_skill)
        val chipgroup = findViewById<ChipGroup>(R.id.chipGroup)
        val back = findViewById<Button>(R.id.backjob)
        val add= findViewById<Button>(R.id.add)
        val text = findViewById<EditText>(R.id.editText)
        chipgroup.setOnCheckedChangeListener { group, checkedId ->
            val chip = chipgroup.findViewById<Chip>(checkedId)
            chipgroup.removeView(chip)
        }
        back.setOnClickListener{
            val jobmenu = Intent(this, ActivityJopApp::class.java)
            startActivity(jobmenu)

        }
        add.setOnClickListener{
            if(!text.text.toString().isEmpty()){
                addChip(text.text.toString())
                text.setText("")
            }
        }
    }


    private fun addChip(text: String){
        val group = findViewById<ChipGroup>(R.id.chipGroup)
        val nchip = Chip(this)
        nchip.text = text
        nchip.isCloseIconVisible = true
        nchip.setOnCloseIconClickListener{
            group.removeView(nchip)
        }
        group.addView(nchip)

    }
}