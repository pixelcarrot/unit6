package com.example.unit6.complex.model

import com.example.unit6.complex.utils.toTime

data class Note(
    val id: String,
    val message: String,
    val timestamp: Long
) {
    fun time(): String = timestamp.toTime()
}
