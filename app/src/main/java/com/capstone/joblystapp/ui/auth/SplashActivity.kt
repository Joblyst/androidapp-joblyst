package com.capstone.joblystapp.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ProgressBar
import com.capstone.joblystapp.R
import com.capstone.joblystapp.ui.main.MainActivity

class SplashActivity : AppCompatActivity() {
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        supportActionBar?.hide()

        progressBar = findViewById(R.id.progressBar)

        progressBar.visibility = View.INVISIBLE

        Handler(Looper.getMainLooper()).postDelayed({
            progressBar.visibility = View.VISIBLE

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 1000)
    }
}
