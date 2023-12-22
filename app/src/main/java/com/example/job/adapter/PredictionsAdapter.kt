package com.example.job.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.job.DetailActivity
import com.example.job.databinding.ItemRowJobsBinding

class PredictionsAdapter(
    private val predictions: List<String>,
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<PredictionsAdapter.PredictionViewHolder>() {

    class PredictionViewHolder(private val binding: ItemRowJobsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(prediction: String) {
            binding.apply {
                jobPositionTv.text = prediction
                Glide.with(itemView.context)
                    .load(prediction)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(gambar)

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_DATA, prediction)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PredictionViewHolder {
        val binding =
            ItemRowJobsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PredictionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PredictionViewHolder, position: Int) {
        val prediction = predictions[position]
        holder.bind(prediction)
    }

    override fun getItemCount() = predictions.size
}
