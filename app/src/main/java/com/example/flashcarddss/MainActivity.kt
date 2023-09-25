package com.example.flashcarddss

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.Button  // Import the Button class

class Task{
    fun onCreate(savedInstanceState: Bundle?) {
        val stringtask = "hello"
    }
}

class MainActivity : AppCompatActivity() {
    private val todoList = mutableListOf<String>() // Define and initialize todoList
    private lateinit var adapter: TodoListAdapter




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize RecyclerView
        adapter = TodoListAdapter(todoList)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewTodoList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Button click listeners
        val buttonAddTodo = findViewById<Button>(R.id.buttonAddTodo)
        buttonAddTodo.setOnClickListener {
            val newTask = "New Task" // You can replace this with user input
            addTask(newTask)
        }





    }

    fun addTask(task: String) {
        if (task.isNotBlank()) {
            todoList.add(task)
            adapter.notifyItemInserted(todoList.size - 1)
            // Optionally, clear the text input field or handle user input
            // Clear the input field: editTextTask.setText("")
        }
    }



//        val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//            val data: Intent? = result.data
//            if (data != null) { // Check that we have data returned
//                val string1 = data.getStringExtra("QUESTIONKEY") // 'string1' needs to match the key we used when we put the string in the Intent
//                val string2 = data.getStringExtra("ANSWERKEY")
//                // Log the value of the strings for easier debugging
//                Log.i("MainActivity", "Question: $string1")
//                Log.i("MainActivity", "Answer: $string2")
//            } else {
//                Log.i("MainActivity", "Returned null data from AddCardActivity")
//            }
//        }

//        findViewById<View>(R.id.myBtn).setOnClickListener {
//            val intent = Intent(this, AddCardActivity::class.java)
//            // Launch EndingActivity with the resultLauncher so we can execute more code
//            // once we come back here from EndingActivity
//            resultLauncher.launch(intent)
//        }
//
//        val flashcardQuestion = findViewById<TextView>(R.id.flashcard_question)
//        val flashcardAnswer = findViewById<TextView>(R.id.flashcard_answer)
//        flashcardQuestion.setOnClickListener {
//            flashcardQuestion.visibility = View.GONE
//            flashcardAnswer.visibility = View.VISIBLE
//        }
//        flashcardAnswer.setOnClickListener {
//            flashcardQuestion.visibility = View.VISIBLE
//            flashcardAnswer.visibility = View.GONE
//        }


}