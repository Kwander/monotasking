package com.example.monotasking

import AddSetActivity
import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.Button
import android.widget.EditText
import android.widget.PopupWindow
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.ItemTouchHelper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken





class MainActivity : AppCompatActivity(), TodoListAdapter.OnItemCheckedListener {
    var todoList = mutableListOf<String>()
    private lateinit var adapter: TodoListAdapter
    private val sharedPreferences by lazy {
        this.getSharedPreferences("TodoListPrefs", Context.MODE_PRIVATE)
    }
    private val gson = Gson()
    var setlist = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonAddTodo = findViewById<Button>(R.id.buttonAddTodo)
        var AddTodoText = findViewById<EditText>(R.id.Addtodotext)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewTodoList)
        recyclerView.layoutManager = LinearLayoutManager(this)



        adapter = TodoListAdapter(this, todoList)
        recyclerView.adapter = adapter

        val itemTouchHelperCallback = SimpleItemTouchHelperCallback(adapter)
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        buttonAddTodo.setOnClickListener {
            val newTask = AddTodoText.text.toString()
            adapter.addTask(newTask)
            AddTodoText.text.clear()
            Log.d("kev", "AddTask Called")
        }

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

                    adapter.submitList(receivedData?.toMutableList() ?: mutableListOf())

                }
            }
        }



        findViewById<View>(R.id.presentTasks).setOnClickListener {
            val intent = Intent(this, SlideshowActivity::class.java)
            intent.putStringArrayListExtra("todoList", ArrayList(todoList))
            resultLauncher.launch(intent)
        }



        val buttonAddSet = findViewById<Button>(R.id.buttonAddSet)
        buttonAddSet.setOnClickListener {
            val popup = AddSetActivity(this, adapter)
            popup.showAtLocation(it, Gravity.CENTER, 0, 0)

            popup.setOnDismissListener {
                // Handle the back button press event (when the popup is dismissed)
                popup.Dismiss()
            }
        }



    }



    override fun onPause() {
        super.onPause()

        val todoListJson = gson.toJson(todoList)
        sharedPreferences.edit().putString("todoList", todoListJson).apply()
    }




    override fun onItemChecked(position: Int, isChecked: Boolean) {
        if (position >= 0 && position < todoList.size) {
            if (isChecked) {
                todoList.removeAt(position)
                adapter.notifyItemRemoved(position)
                Log.d("kev", "Item Is Checked, Removed")
            }
        }
    }


}