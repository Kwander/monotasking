package com.example.flashcarddss

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2

class SlideshowActivity(
    private val todoList: MutableList<String>
) : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_slideshow)

        val viewPager: ViewPager2 = findViewById(R.id.viewPager)
        viewPager.adapter = MainActivity.TodoListAdapterSingleton.adapter

        val resultIntent = Intent()
        resultIntent.putStringArrayListExtra("data", ArrayList(todoList))
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }
}

