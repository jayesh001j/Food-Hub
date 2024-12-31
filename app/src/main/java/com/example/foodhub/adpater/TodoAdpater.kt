package com.example.my_app.adpater

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.foodhub.AddTodoActivity
import com.example.foodhub.R
import com.example.foodhub.databinding.TodoItemBinding

import com.example.my_app.helper.FireStoreHelper.Companion.fireStoreHelper
import com.example.my_app.models.TodoModel

class TodoAdpater(val l1:MutableList<TodoModel>) : RecyclerView.Adapter<TodoAdpater.DataViewHolder>() {

    class DataViewHolder(itemView : View) : ViewHolder(itemView) {
        val binding = TodoItemBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
       val view =  LayoutInflater.from(parent.context).inflate(R.layout.todo_item,parent,false)

        return  DataViewHolder(view)
    }

    override fun getItemCount(): Int {
       return  l1.size
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.binding.todoTitle.text = l1[position].title
        holder.binding.todoDesc.text = l1[position].desc

        holder.binding.todoEdit.setOnClickListener {
            val intent = Intent(holder.itemView.context, AddTodoActivity::class.java)

            intent.putExtra("uid",l1[position].uid)
            intent.putExtra("title",l1[position].title)
            intent.putExtra("desc",l1[position].desc)

            holder.itemView.context.startActivity(intent)
        }

        holder.binding.deleteTodo.setOnClickListener {
            fireStoreHelper.deleteTodo(l1[position].uid!!)
            Toast.makeText(holder.itemView.context, "Deleted Successfully..", Toast.LENGTH_SHORT).show()
        }
    }
}