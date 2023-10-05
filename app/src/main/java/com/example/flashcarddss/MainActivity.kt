package com.example.flashcarddss

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.Button  // Import the Button class
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.flashcarddss.TodoListAdapter
import com.google.android.material.snackbar.Snackbar
import java.io.Serializable


class SerializableObject(private val onItemCheckedListener: TodoListAdapter.OnItemCheckedListener) : Serializable {
    fun getOnItemCheckedListener(): TodoListAdapter.OnItemCheckedListener {
        return onItemCheckedListener
    }
}



class MainActivity : AppCompatActivity(), TodoListAdapter.OnItemCheckedListener {
    var todoList = mutableListOf<String>() // Define and initialize todoList
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

        val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                if (data != null) {
                    val receivedData = data.getStringArrayListExtra("data")

                    // Update the visual representation of your RecyclerView
                    adapter.submitList(receivedData?.toMutableList() ?: mutableListOf())

                    // Update your view or perform other operations with todoList
//                    val onItemCheckedListener = intent.getSerializableExtra("onItemCheckedListener") as TodoListAdapter.OnItemCheckedListener
                }
            }
        }



        findViewById<View>(R.id.presentTasks).setOnClickListener {
            val intent = Intent(this, SlideshowActivity::class.java)
            // Launch EndingActivity with the resultLauncher so we can execute more code
            // once we come back here from EndingActivity
            intent.putStringArrayListExtra("todoList", ArrayList(todoList))

            resultLauncher.launch(intent)
//            overridePendingTransition(R.anim.right_in, R.anim.left_in)
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


}