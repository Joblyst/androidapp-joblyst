package com.example.job.fragments

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.job.model.Job
import com.example.job.response.JobsItem

class JobDetailsDialogFragment : DialogFragment() {
    companion object {
        fun newInstance(jobsItem: Job): JobDetailsDialogFragment {
            val fragment = JobDetailsDialogFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
