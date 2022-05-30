package com.example.unit6.complex.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun Long.toTime(): String {
    return SimpleDateFormat("hh:mm:ss").format(Date(this))
}
