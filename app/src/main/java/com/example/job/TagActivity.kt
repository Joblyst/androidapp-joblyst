package com.example.job

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TagActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tag)

        // Mengambil referensi ke floating action button
        val fab = findViewById<FloatingActionButton>(R.id.fab)

        // Mengatur OnClickListener untuk floating action button
        fab.setOnClickListener { // Ketika floating action button ditekan, pindah ke MainActivity
            val intent = Intent(this@TagActivity, MainActivity::class.java)
            startActivity(intent)
        }

        // Mengambil referensi ke setiap card
        val card1 = findViewById<CardView>(R.id.cardView1)
        val card2 = findViewById<CardView>(R.id.cardView2)
        val card3 = findViewById<CardView>(R.id.cardView3)

        // Mengatur OnClickListener untuk setiap card
        card1.setOnClickListener { // Ketika card 1 ditekan, simpan data ke API (tambahkan logika sesuai kebutuhan Anda)
            // Contoh:
            saveToApi("Accounting and Finance")
        }
        card2.setOnClickListener { // Ketika card 2 ditekan, simpan data ke API (tambahkan logika sesuai kebutuhan Anda)
            // Contoh:
            saveToApi("Administration")
        }
        card3.setOnClickListener { // Ketika card 3 ditekan, simpan data ke API (tambahkan logika sesuai kebutuhan Anda)
            // Contoh:
            saveToApi("Business Development and Sales")
        }
    }

    // Method untuk menyimpan data ke API (gantilah sesuai kebutuhan Anda)
    private fun saveToApi(category: String) {
        // Logika untuk menyimpan data ke API
    }
}
