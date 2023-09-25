package com.example.flashcarddss

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView

class AddCardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_card)

        val TheQuestion = findViewById<EditText>(R.id.editQuestion)
        val TheAnswer = findViewById<EditText>(R.id.editAnswer)
        //editAnswer editQuestion

        findViewById<ImageView>(R.id.myReturnBtn).setOnClickListener {
            finish()
        }
        findViewById<ImageView>(R.id.SaveBtn).setOnClickListener {
            val data = Intent()

            val questionString = TheQuestion.text.toString()
            val answerString = TheAnswer.text.toString()

            data.putExtra("QUESTIONKEY", questionString)
            data.putExtra("ANSWERKEY", answerString)

            setResult(RESULT_OK, data) // set result code and bundle data for response

            finish() // closes this activity and pass data to the original activity that launched this activity
        }


    }
}