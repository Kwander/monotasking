package com.example.flashcarddss

import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.flashcarddss.R  // Replace with your actual package name

class SlideshowAdapter(private val taskList: MutableList<String>) : RecyclerView.Adapter<SlideshowAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskCheckBox: CheckBox = itemView.findViewById(R.id.checkkbox) // Replace with your CheckBox ID
        val taskEditText: EditText = itemView.findViewById(R.id.taskText) // Replace with your EditText ID

        fun check(){
            taskCheckBox.isChecked = false
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.slideshow_item, parent, false) // Replace with your item layout XML
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = taskList[position]
        val editableTask: Editable = Editable.Factory.getInstance().newEditable(task)
        holder.taskEditText.text = editableTask

        // Set up CheckBox click listener to remove item from the list
        holder.taskCheckBox.setOnCheckedChangeListener { _, isChecked ->
            holder.check()
            if (isChecked) {
                taskList.removeAt(position)
                notifyItemRemoved(position)
            }
        }
    }


    override fun getItemCount(): Int {
        return taskList.size
    }
}




