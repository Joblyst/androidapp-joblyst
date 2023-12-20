package com.example.job

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.example.job.databinding.ActivityRegisterBinding
import com.example.job.helper.ResultState
import com.example.job.viewmodels.RegisterViewModel
import com.example.job.viewmodels.ViewModelFactory

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModelFactory: ViewModelFactory
    private val registerViewModel: RegisterViewModel by viewModels{viewModelFactory}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val window = window
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        viewModelFactory = ViewModelFactory.getInstance(this)
        supportActionBar?.hide()

        pushRegister()

        binding.loginHereTextView.setOnClickListener {
            intentToLogin()
        }
    }

    private fun pushRegister(){
        binding.registerButton.setOnClickListener {
            val name = binding.editTextTextPersonName.text.toString()
            val email = binding.emailedRegister.text.toString()
            val password = binding.passwordedRegister.text.toString()
            if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()){
                val result = registerViewModel.register(name, email, password)
                result.observe(this){
                    when(it){
                        is ResultState.Error -> {
                            binding.progressBar.visibility = View.INVISIBLE
                            val error = it.error
                            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
                        }
                        is ResultState.Success -> {
                            binding.progressBar.visibility = View.INVISIBLE
                            Toast.makeText(this, "register_success", Toast.LENGTH_SHORT).show()
                            intentToLogin()
                        }
                        is ResultState.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }

    private fun intentToLogin(){
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}