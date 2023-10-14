package com.example.flashcarddss

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2

class SlideshowActivity() : AppCompatActivity() {
    private lateinit var todoList: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_slideshow)

        todoList =  intent.getStringArrayListExtra("todoList") ?: ArrayList()

        val viewPager: ViewPager2 = findViewById(R.id.viewPager)
        val adapter = SlideshowAdapter(todoList)
        viewPager.adapter = adapter





        findViewById<View>(R.id.bottomLeftElement).setOnClickListener {
            setResultAndFinish(todoList)
        }
    }

    override fun onBackPressed() {
        // Perform your desired actions here before finishing the activity
        setResultAndFinish(todoList)

    }



    private fun setResultAndFinish(todoList: ArrayList<String>) {
        val resultIntent = Intent()
        resultIntent.putStringArrayListExtra("data", todoList)
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }


}

