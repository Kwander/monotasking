package com.example.flashcarddss

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
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
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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
        this.getSharedPreferences("TodoListPrefs", Context.MODE_PRIVATE)
    }
    private val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize EditText and Button
        val buttonAddTodo = findViewById<Button>(R.id.buttonAddTodo)
        var AddTodoText = findViewById<EditText>(R.id.Addtodotext)

        // Initialize RecyclerView with the OnItemCheckedListener callback
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewTodoList)
        recyclerView.layoutManager = LinearLayoutManager(this)



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

        // Load saved todo list data
        val savedTodoListJson = sharedPreferences.getString("todoList", null)
        val type = object : TypeToken<List<String>>() {}.type
        val savedTodoList = gson.fromJson<List<String>>(savedTodoListJson, type) ?: mutableListOf()
        if (!savedTodoList.isNullOrEmpty()) {
            todoList.addAll(savedTodoList)
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
            // Launch EndingActivity with the resultLauncher so we can execute more code
            // once we come back here from EndingActivity
            val intent = Intent(this, SlideshowActivity::class.java)
            intent.putStringArrayListExtra("todoList", ArrayList(todoList))
            resultLauncher.launch(intent)
            resultLauncher.launch(intent)
//            overridePendingTransition(R.anim.right_in, R.anim.left_in)
        }






    }
    override fun onPause() {
        super.onPause()

        val todoListJson = gson.toJson(todoList)
        sharedPreferences.edit().putString("todoList", todoListJson).apply()
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