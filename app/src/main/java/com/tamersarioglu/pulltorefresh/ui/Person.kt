package com.tamersarioglu.pulltorefresh.ui

import androidx.compose.runtime.mutableStateListOf
import com.tamersarioglu.pulltorefresh.R

data class Person(
    val name: String,
    val age: Int,
    val personImage: Int
)

// Initial list of persons with one item
val listOfPersons = mutableStateListOf(
    Person("Tamer", 41, R.drawable.person_place_holder)
)