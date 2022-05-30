package com.example.unit6.complex.api.model

import com.google.gson.annotations.SerializedName

class NoteApiModel {
    @SerializedName("message")
    var message: String = ""

    @SerializedName("timestamp")
    var timestamp: Long = System.currentTimeMillis()
}
