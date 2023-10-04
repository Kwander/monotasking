package com.example.flashcarddss

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.Collections

interface ItemTouchHelperAdapter {
    fun onItemMove(fromPosition: Int, toPosition: Int)
}

class TodoListAdapter(
    private val onItemCheckedListener: OnItemCheckedListener,
    private val todoList: MutableList<String> // Add this property
) : RecyclerView.Adapter<TodoListAdapter.ViewHolder>(), ItemTouchHelperAdapter {


    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        Collections.swap(todoList, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
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

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = todoList[position]
        Log.d("kev","OnBindViewHolder")
        holder.bind(task)
        // Set up the checkbox listener
        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            Log.d("kev", "Passed Check listener. Now calling onItemCheckedListener")
            onItemCheckedListener.onItemChecked(position, isChecked)
        }
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val taskTextView: TextView = itemView.findViewById(R.id.todo_text)
        val checkBox: CheckBox = itemView.findViewById(R.id.todo_icon) // Corrected ID

        fun bind(task: String) {
            taskTextView.text = task
        }
    }
}

