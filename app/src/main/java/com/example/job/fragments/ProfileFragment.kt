package com.example.job.fragments

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.job.MainActivity
import com.example.job.ProfileAbout
import com.example.job.ProfileTheme
import com.example.job.R
import com.example.job.WelcomeActivity
import com.example.job.databinding.FragmentProfileBinding
import com.example.job.response.User
import com.example.job.viewmodels.ProfileViewModel
import com.example.job.viewmodels.ViewModelFactory

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private var token = ""
    private lateinit var data: User
    private val profileViewModel: ProfileViewModel by viewModels { ViewModelFactory.getInstance(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profileViewModel.checkToken().observe(viewLifecycleOwner) {
            token = it.token
            if (!it.isLogin) {
                val intent = Intent(requireActivity(), WelcomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                requireActivity().finish()
            }
        }
        profileViewModel.getUser().observe(viewLifecycleOwner){
            token = it.token

            profileViewModel.getDataUser(token)
            profileViewModel.dataProfile.observe(viewLifecycleOwner){
                data = it
            }
            setupDataUser()
        }
        binding.logoutButton.setOnClickListener {
            profileViewModel.logout()
        }
        binding.rTema.setOnClickListener{
            movetotheme()
        }
        binding.rLanguage.setOnClickListener{
            movetosetting()
        }
        binding.rAbout.setOnClickListener {
            movetoabout()
        }
    }
    private fun movetotheme(){
        val intent = Intent(requireContext(), ProfileTheme::class.java)
        startActivity(intent)
    }

    private fun movetosetting(){
        startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
    }

    private fun movetoabout(){
        val intent = Intent(requireContext(), ProfileAbout::class.java)
        startActivity(intent)
    }

    private fun setupDataUser() {
        profileViewModel.dataProfile.observe(viewLifecycleOwner) {
            if (it != null) {
                data = it
                showProfile(data)
            }
        }
    }
    private fun showProfile(data : User) {
        Glide.with(this)
            .load(data.photoProfile)
            .circleCrop()
            .placeholder(R.drawable.logo_profile)
            .error(R.drawable.logo_profile)
            .into(binding.imgProfile)

        binding.nameUser.text = data.username
        binding.emailUser.text = data.email
    }
}