package com.example.job.fragments

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.job.adapter.PredictionsAdapter
import com.example.job.adapter.SearchAdapter
import com.example.job.databinding.FragmentBookmarkBinding
import com.example.job.helper.ResultState
import com.example.job.model.Job
import com.example.job.response.JobsItem
import com.example.job.viewmodels.SearchViewModel
import com.example.job.viewmodels.ViewModelFactory

class BookmarkFragment : Fragment() {
    private var _binding: FragmentBookmarkBinding? = null
    private val binding get() = _binding!!
    private var token = ""
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

        searchViewModel.predictions.observe(viewLifecycleOwner) { predictions ->
            displayPredictions(predictions)
        }

        searchViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            showLoading(isLoading)
        }

        searchViewModel.article.observe(viewLifecycleOwner) { jobsItem ->
            searchJob(jobsItem)
        }

        binding.searchView1.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    searchViewModel.findPredictions(query)
                    binding.searchView1.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    searchViewModel.findPredictions(newText)
                }
                return true
            }
        })

        return root
    }

    private fun displayPredictions(predictions: List<String>) {
        val adapter = PredictionsAdapter(predictions) { prediction ->
            searchViewModel.getJobByPosition(token, prediction)
                .observe(viewLifecycleOwner) { result ->
                    when (result) {
                        is ResultState.Success -> {
                            // Assuming result.data is a single Job object
                            showJobDetailsDialog(result.data)
                        }
                        is ResultState.Error -> {
                            Toast.makeText(
                                requireContext(),
                                "Error fetching details",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        ResultState.Loading -> {
                            // Optionally handle loading state
                        }
                    }
                }
        }
        binding.rvArticle1.adapter = adapter
    }

    private fun showJobDetailsDialog(jobsItem: Job) {
        // Create and show a dialog with job details
        // Example:
        val dialog = JobDetailsDialogFragment.newInstance(jobsItem)
        dialog.show(childFragmentManager, "JobDetails")
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}