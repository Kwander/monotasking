package com.example.monotasking

import android.content.res.Resources
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView

class SlideshowAdapter(private val taskList: MutableList<String>) : RecyclerView.Adapter<SlideshowAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskCheckBox: CheckBox = itemView.findViewById(R.id.checkkbox)
        val taskEditText: EditText = itemView.findViewById(R.id.taskText)

        fun check(){
            taskCheckBox.isChecked = false
        }

        fun bind(todo: String) {
            val screenHeight = Resources.getSystem().displayMetrics.heightPixels
            val textSize = screenHeight * 0.05f // Adjust the factor as needed

            taskEditText.setText(todo)
            taskEditText.textSize = textSize
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.slideshow_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = taskList[position]
        val editableTask: Editable = Editable.Factory.getInstance().newEditable(task)
        holder.taskEditText.text = editableTask
        holder.bind(task)

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




