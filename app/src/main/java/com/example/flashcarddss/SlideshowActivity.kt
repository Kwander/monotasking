package com.example.flashcarddss

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2

class SlideshowActivity() : AppCompatActivity() {
    private lateinit var adapter: TodoListAdapter
    private lateinit var todoList: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_slideshow)

        todoList = intent.getStringArrayListExtra("todoList") ?: ArrayList()

        val viewPager: ViewPager2 = findViewById(R.id.viewPager)
        adapter = TodoListAdapter(object : TodoListAdapter.OnItemCheckedListener {
            override fun onItemChecked(position: Int, isChecked: Boolean) {
                if (position >= 0 && position < todoList.size) {
                    if (isChecked) {
                        todoList.removeAt(position)
                        adapter.notifyItemRemoved(position)
                        Log.d("kev", "Item Is Checked, Removed")
                    }
                    // Handle logic when item is unchecked if needed
                }
            }
        }, todoList)
//        viewPager.adapter = adapter

        findViewById<View>(R.id.bottomLeftElement).setOnClickListener {
            setResultAndFinish(todoList)
        }
    }

    private fun setResultAndFinish(todoList: ArrayList<String>) {
        val resultIntent = Intent()
        resultIntent.putStringArrayListExtra("data", todoList)
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }


}

