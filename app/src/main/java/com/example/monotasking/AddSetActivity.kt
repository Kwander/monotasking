import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.PopupWindow
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.monotasking.R
import com.example.monotasking.SimpleItemTouchHelperCallback
import com.example.monotasking.TodoListAdapter

class AddSetActivity(
    context: Context,
    val adapter: TodoListAdapter
) : PopupWindow(context) {
    val OldTodoList = adapter.getTodoList().toMutableList() //this ones a copy

    init {
        val inflater = LayoutInflater.from(context)
        val popupView = inflater.inflate(R.layout.add_set, null)
        val TodoList = adapter.getTodoList() //this isnt a copy ig
        TodoList.clear()
        adapter.notifyDataSetChanged()


        val recyclerView = popupView.findViewById<RecyclerView>(R.id.recyclerViewpop)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        val itemTouchHelperCallback = SimpleItemTouchHelperCallback(adapter)
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        val editText = popupView.findViewById<EditText>(R.id.Addtodotextpop)
        popupView.findViewById<Button>(R.id.buttonAddTodopop).setOnClickListener(){
            val newTask = editText.text.toString()
            adapter.addTaskNormally(newTask)
            editText.text.clear()
        }


        contentView = popupView
        width = WindowManager.LayoutParams.MATCH_PARENT
        height = WindowManager.LayoutParams.MATCH_PARENT
        isFocusable = true


        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Not used
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Not used
            }

            override fun afterTextChanged(s: Editable?) {
                if (s != null && s.isNotEmpty() && s.toString().endsWith("\n")) {
                    // Remove the newline character and call your function
                    s.replace(s.length - 1, s.length, "")
                    Log.d("kev", "Enter pressed")
                    val newTask = editText.text.toString()
                    adapter.addTaskNormally(newTask)
                    editText.text.clear()
                }
            }
        })

    }

    fun Dismiss() {
        adapter.addTasksFromOtherList(OldTodoList)
        Log.d("kev", "Dismissed")
    }

}
