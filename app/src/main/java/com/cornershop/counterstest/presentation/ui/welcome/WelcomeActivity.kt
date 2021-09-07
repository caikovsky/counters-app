package com.cornershop.counterstest.presentation.ui.welcome

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cornershop.counterstest.databinding.ActivityWelcomeBinding
import com.cornershop.counterstest.presentation.ui.main.MainActivity
import com.cornershop.counterstest.util.Constants.FIRST_ACCESS_SHARED_KEY
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WelcomeActivity : AppCompatActivity() {

    private var _binding: ActivityWelcomeBinding? = null
    private val binding: ActivityWelcomeBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val sharedPreferencesName = "${packageName}_preferences"
        val sharedPref = getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE)
        val firstAccess = sharedPref.getBoolean(FIRST_ACCESS_SHARED_KEY, true)

        binding.welcomeContent?.buttonStart?.setOnClickListener {
            if (firstAccess) {
                sharedPref.edit().putBoolean(FIRST_ACCESS_SHARED_KEY, false).apply()
            }

            goToMainActivity()
        }
    }

    private fun goToMainActivity() {
        startActivity(
            Intent(
                this,
                MainActivity::class.java
            ).apply {
                flags =
                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            })
    }
}
