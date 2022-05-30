package com.example.unit6.simple

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.unit6.databinding.ActivitySimpleBinding

class SimpleActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySimpleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySimpleBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}
