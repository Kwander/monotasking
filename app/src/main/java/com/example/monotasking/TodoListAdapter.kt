package com.example.monotasking

import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import java.util.Collections

interface ItemTouchHelperAdapter {
    fun onItemMove(fromPosition: Int, toPosition: Int)
}

class TodoListAdapter(
    val onItemCheckedListener: OnItemCheckedListener,
    private val todoList: MutableList<String>
) : RecyclerView.Adapter<TodoListAdapter.ViewHolder>(), ItemTouchHelperAdapter {


    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        Collections.swap(todoList, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
    }

    fun addTask(task: String) {
        if (task.isNotBlank()) {
            todoList.add(0 ,task)
            notifyItemInserted(0)
        }
    }

    fun addTaskNormally(task: String) {
        if (task.isNotBlank()) {
            todoList.add(task)
            notifyItemInserted(todoList.size - 1) // Notify the adapter about the inserted item at the last position
        }
    }


    interface OnItemCheckedListener {


        fun onItemChecked(position: Int, isChecked: Boolean)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.todo_item, parent, false)
        Log.d("kev","OnCreateViewholder")
        return ViewHolder(view)
    }

    fun submitList(newList: List<String>) {
        todoList.clear()
        todoList.addAll(newList)
        notifyDataSetChanged()
    }

    fun addTasksFromOtherList(otherList: List<String>) {
        if (otherList.isNotEmpty()) {
            todoList.addAll(otherList)
            notifyItemRangeInserted(todoList.size - otherList.size, otherList.size)
        }
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = todoList[position]
        Log.d("kev","OnBindViewHolder")
        holder.bind(task)

        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            holder.check()
            Log.d("kev", "OnBindViewHolder::Passed Check listener. Now calling onItemCheckedListener")
            onItemCheckedListener.onItemChecked(holder.adapterPosition, isChecked)
        }



    }



    override fun getItemCount(): Int {
        return todoList.size
    }
    fun getTodoList(): MutableList<String> {
        return todoList
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskTextView: EditText = itemView.findViewById(R.id.todo_text)
        val checkBox: CheckBox = itemView.findViewById(R.id.todo_icon)

        fun bind(task: String) {
            val editableTask: Editable = Editable.Factory.getInstance().newEditable(task)
            taskTextView.text = editableTask
        }
        fun check(){
            checkBox.isChecked = false
        }
    }




}

