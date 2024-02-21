package com.example.monotasking

import android.content.res.Resources
import android.text.Editable
import android.util.Log
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SlideshowAdapter(private val taskList: MutableList<String>) : RecyclerView.Adapter<SlideshowAdapter.ViewHolder>() {
    private var lastTapTime: Long = 0
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //        val taskCheckBox: CheckBox = itemView.findViewById(R.id.checkkbox)
        private var lastTapTime: Long = 0 // for double tapping
        val taskEditText: TextView = itemView.findViewById(R.id.taskText)

//        fun check(){
//            taskCheckBox.isChecked = false
//        }

        fun bind(todo: String) {
            val screenHeight = Resources.getSystem().displayMetrics.heightPixels
            val todoLength = todo.length
            var textSize = when {
                todoLength < 10 -> screenHeight * 0.07f
                todoLength < 20 -> screenHeight * 0.043f
                todoLength < 30 -> screenHeight * 0.035f
                todoLength < 50 -> screenHeight * 0.03f
                else -> screenHeight * 0.025f
            }

            taskEditText.setText(todo)
            taskEditText.textSize = textSize

            itemView.setOnTouchListener { _, event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        val currentTime = System.currentTimeMillis()
                        val timeSinceLastTap = currentTime - lastTapTime
                        lastTapTime = currentTime

                        // Check if it's a double tap (within a certain time threshold)
                        if (timeSinceLastTap < 400) { //threshold for double tap
                            // Find the corresponding task
                            val position = adapterPosition
                            if (position != RecyclerView.NO_POSITION) {
                                taskList.removeAt(position)
                                notifyItemRemoved(position)




                            }
                        }
                    }
                }
                true
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




