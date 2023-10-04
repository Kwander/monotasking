package com.example.flashcarddss

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.Button  // Import the Button class
import android.widget.EditText
import androidx.recyclerview.widget.ItemTouchHelper


class Task{
    fun onCreate(savedInstanceState: Bundle?) {
        val stringtask = "hello"
    }
}

class MainActivity : AppCompatActivity(), TodoListAdapter.OnItemCheckedListener {
    val todoList = mutableListOf<String>() // Define and initialize todoList
    private lateinit var adapter: TodoListAdapter
    private val sharedPreferences by lazy {
        getSharedPreferences("TodoListPrefs", Context.MODE_PRIVATE)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize EditText and Button
        val buttonAddTodo = findViewById<Button>(R.id.buttonAddTodo)
        var AddTodoText = findViewById<EditText>(R.id.Addtodotext)

        // Initialize RecyclerView with the OnItemCheckedListener callback
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewTodoList)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Load saved todo list data
        val savedTodoList = sharedPreferences.getStringSet("todoList", HashSet<String>())?.toList()
        if (!savedTodoList.isNullOrEmpty()) {
            todoList.addAll(savedTodoList)
        }

        adapter = TodoListAdapter(this, todoList)
        recyclerView.adapter = adapter

        val itemTouchHelperCallback = SimpleItemTouchHelperCallback(adapter)
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        buttonAddTodo.setOnClickListener {
            val newTask = AddTodoText.text.toString()
            addTask(newTask)
            AddTodoText.text.clear()
            Log.d("kev", "AddTask Called")
        }





    }
    override fun onPause() {
        super.onPause()
        // Save the todo list data when the app is paused
        sharedPreferences.edit().putStringSet("todoList", HashSet(todoList)).apply()
    }


    fun addTask(task: String) {
        if (task.isNotBlank()) {
            todoList.add(task)
            adapter.notifyItemInserted(todoList.size - 1)
            // Optionally, clear the text input field or handle user input
            // Clear the input field: editTextTask.setText("")
        }
    }

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