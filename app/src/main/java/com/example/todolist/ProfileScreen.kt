package com.example.todolist

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.todolist.ui.theme.TodolistTheme

@Composable
fun ProfileScreen(){
    TodolistTheme {
        Text(text = "This is Profile Screen")
    }
}