package com.example.job

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.job.databinding.ActivityDetailBinding
import com.example.job.response.JobsItem

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val window = window
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        supportActionBar?.hide()

        detail()

        binding.fab.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

    }

    private fun detail(){
        val data = intent.getParcelableExtra<JobsItem>(EXTRA_DATA) as JobsItem
        binding.apply {
            jobPosition.text = data.jobPosition
            office.text = data.office
            city.text = data.city
            jobLevel.text = data.jobLevel
            workTime.text = data.workTime
            salary.text = data.salary
            qualification.text = data.qualifications.toString()
            Glide.with(this@DetailActivity)
                .load(data.officeLogo)
                .fitCenter()
                .into(officeLogo)
        }
    }

    companion object {
        const val EXTRA_DATA = "extra_data"
    }
}