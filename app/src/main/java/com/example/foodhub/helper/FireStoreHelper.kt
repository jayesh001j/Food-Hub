package com.example.my_app.helper

import AuthHelper.Companion.authHelper
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.example.my_app.adpater.TodoAdpater
import com.example.my_app.models.TodoModel

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class FireStoreHelper {

    companion object {
        val fireStoreHelper = FireStoreHelper()
    }

    val fireStore = Firebase.firestore
    val colletionName = "MyTodo";
    val notesColletionName = "notes";
    val currentUserEmail = authHelper.auth.currentUser!!.email.toString();

    // TODO : Title and Description add
    fun addTodo(model: TodoModel) {

        fireStore.collection(colletionName).document(currentUserEmail)
            .collection("notes").add(model)
    }

    fun addUserData(email: String, password: String) {

        val data = hashMapOf(
            "email" to email,
            "password" to password
        )

        fireStore.collection(colletionName).document(currentUserEmail).set(data)
    }


    // TODO : Title and Description Fetch
    fun fetchTodoData(recyclerView: RecyclerView) {

        var todoList = mutableListOf<TodoModel>()

        fireStore.collection(colletionName).document(currentUserEmail)
            .collection(notesColletionName).addSnapshotListener { value, error ->
                if (value != null && !value.isEmpty) {
                    todoList.clear()

                    for (e in value.documents) {
                        val uid = e.id.toString()
                        val title = e.data!!.get("title").toString()
                        val desc = e.data!!.get("desc").toString()

                        val data = TodoModel(uid, title, desc)

                        todoList.add(data)
                    }
                    val adpater = TodoAdpater(todoList)
                    recyclerView.adapter = adpater
                } else {
                    Log.e("Error", "fetchTodoData: ${error!!.message} ")
                }
            }
    }

    // TODO : Title and Description Update
    fun updateTodo(model: TodoModel) {
        fireStore.collection(colletionName).document(currentUserEmail)
            .collection(notesColletionName).document(model.uid!!).set(model)
    }

    // TODO : Title and Description Delete
    fun deleteTodo(uid: String) {
        fireStore.collection(colletionName).document(currentUserEmail)
            .collection(notesColletionName).document(uid).delete()
    }
}