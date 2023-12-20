package com.example.job.fragments

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.job.adapter.SearchAdapter
import com.example.job.databinding.FragmentBookmarkBinding
import com.example.job.response.JobsItem
import com.example.job.viewmodels.SearchViewModel
import com.example.job.viewmodels.ViewModelFactory

class BookmarkFragment : Fragment() {
    private var _binding: FragmentBookmarkBinding? = null
    private val binding get() = _binding!!
    private var token= ""
    private lateinit var factory: ViewModelFactory
    private val searchViewModel: SearchViewModel by viewModels { factory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        val root: View = binding.root

        factory = ViewModelFactory.getInstance(binding.root.context)
        setupView(root.context)

        searchViewModel.checkToken().observe(viewLifecycleOwner) {
            token = it.token
        }

        searchViewModel.article.observe(viewLifecycleOwner){
            searchJob(it)
        }
        searchViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            showLoading(isLoading)
        }

        binding?.apply {
            searchView1.setOnQueryTextListener(object : SearchView.OnQueryTextListener,android.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    showLoading(true)
                    if (query != null) {
                        searchViewModel.findJobs(token,query)
                        searchView1.clearFocus()
                    }
                    return true
                }
                override fun onQueryTextChange(newText: String?): Boolean {
                    showLoading(true)
                    if (newText != null) {
                        searchViewModel.findJobs(token,newText)
                    }
                    return false
                }
            })
        }

        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun searchJob(item: List<JobsItem>) {
        val adapter = SearchAdapter(item)
        binding.rvArticle1.adapter = adapter
    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar1.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setupView(context: Context) {
        val storiesRv = binding.rvArticle1

        if (context.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            storiesRv.layoutManager = GridLayoutManager(context, 2)
        } else {
            storiesRv.layoutManager = LinearLayoutManager(context)
        }

    }




}