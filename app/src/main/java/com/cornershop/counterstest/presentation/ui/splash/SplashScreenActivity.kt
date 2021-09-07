package com.cornershop.counterstest.presentation.ui.splash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.cornershop.counterstest.R
import com.cornershop.counterstest.presentation.ui.main.MainActivity
import com.cornershop.counterstest.presentation.ui.welcome.WelcomeActivity
import com.cornershop.counterstest.util.Constants.FIRST_ACCESS_SHARED_KEY
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val sharedPreferencesName = "${packageName}_preferences"
        val sharedPref = getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE)
        val firstAccess = sharedPref.getBoolean(FIRST_ACCESS_SHARED_KEY, true)

        lifecycleScope.launch {
            delay(1500)

            if (firstAccess) {
                goToWelcomeActivity()
            } else {
                goToMainActivity()
            }
        }
    }

    private fun goToWelcomeActivity() {
        startActivity(
            Intent(
                this,
                WelcomeActivity::class.java
            ).apply {
                flags =
                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            })
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