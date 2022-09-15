package com.amnah.sw.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amnah.sw.R
import com.amnah.sw.data.domain.WorkoutResponseItem
import com.amnah.sw.databinding.ItemWorkoutBinding
import com.bumptech.glide.Glide

class WorkoutAdapter(
    private val workoutList: List<WorkoutResponseItem>,
) : RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder>() {

    inner class WorkoutViewHolder(val binding: ItemWorkoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutViewHolder {
        val view = ItemWorkoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WorkoutViewHolder(view)
    }

    override fun onBindViewHolder(holder: WorkoutViewHolder, position: Int) {
        val currentPosition = workoutList[position]

        holder.binding.apply {
            Glide.with(imageWorkout.context).load(currentPosition.gifUrl)
                .placeholder(R.drawable.ic_workout)
                .into(imageWorkout)

            bodyPart.text = currentPosition.bodyPart
            name.text = currentPosition.name
            equipment.text = currentPosition.equipment
        }
    }

    override fun getItemCount() = workoutList.size
}