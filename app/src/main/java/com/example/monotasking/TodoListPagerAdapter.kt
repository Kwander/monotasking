package com.example.monotasking

import android.content.res.Resources
import android.text.Editable
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SlideshowAdapter(private val taskList: MutableList<String>) : RecyclerView.Adapter<SlideshowAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val taskCheckBox: CheckBox = itemView.findViewById(R.id.checkkbox)
        val taskEditText: TextView = itemView.findViewById(R.id.taskText)

//        fun check(){
//            taskCheckBox.isChecked = false
//        }

        fun bind(todo: String) {
            val screenHeight = Resources.getSystem().displayMetrics.heightPixels
            val textSize = screenHeight * 0.05f // Adjust the factor as needed

            taskEditText.setText(todo)
            taskEditText.textSize = textSize

            // Double tap listener for the entire item view
            itemView.setOnTouchListener { _, event ->
                if (event.action == MotionEvent.ACTION_DOWN) {
                    // Find the corresponding task
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
//                        val task = taskList[position]
//                        // Update task status (e.g., mark as done)
//                        // Example: task.isDone = true
//                        // Notify adapter
//                        notifyItemChanged(position)
                        taskList.removeAt(position)
                        notifyItemRemoved(position)
                    }
                }
                true // Return true to consume the touch event
            }
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

//        holder.taskCheckBox.setOnCheckedChangeListener { _, isChecked ->
//            holder.check()
//            if (isChecked) {
//                taskList.removeAt(position)
//                notifyItemRemoved(position)
//            }
//        }
    }


    override fun getItemCount(): Int {
        return taskList.size
    }
}




