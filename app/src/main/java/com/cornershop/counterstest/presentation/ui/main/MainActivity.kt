package com.cornershop.counterstest.presentation.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cornershop.counterstest.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
