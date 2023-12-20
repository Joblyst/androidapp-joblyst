package com.example.job.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.job.DetailActivity
import com.example.job.DetailActivity.Companion.EXTRA_DATA
import com.example.job.databinding.ItemRowJobsBinding
import com.example.job.response.JobsItem

class JobsAdapter : PagingDataAdapter<JobsItem, JobsAdapter.ListViewHolder>(DIFF_ITEM_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ItemRowJobsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    class ListViewHolder(private val binding: ItemRowJobsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: JobsItem) {
            binding.apply {
                jobPositionTv.text = item.jobPosition
                companyNameTv.text = item.office
                workHourTv.text = item.workTime
                workRegionTv.text = item.city
                textView5.text = item.salary
                Glide.with(itemView.context)
                    .load(item.officeLogo)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(gambar)

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra(EXTRA_DATA, item)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    companion object {
        val DIFF_ITEM_CALLBACK = object : DiffUtil.ItemCallback<JobsItem>() {
            override fun areItemsTheSame(
                oldJobs: JobsItem,
                newJobs: JobsItem
            ): Boolean {
                return oldJobs == newJobs
            }

            override fun areContentsTheSame(
                oldJobs: JobsItem,
                newJobs: JobsItem
            ): Boolean {
                return oldJobs.jobPosition == newJobs.jobPosition &&
                        oldJobs.office == newJobs.office &&
                        oldJobs.workTime == newJobs.workTime &&
                        oldJobs.city == newJobs.city &&
                        oldJobs.salary == newJobs.salary &&
                        oldJobs.officeLogo == newJobs.officeLogo
            }
        }
    }
}