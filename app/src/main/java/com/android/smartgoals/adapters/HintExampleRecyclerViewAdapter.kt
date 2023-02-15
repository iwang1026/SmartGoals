package com.android.smartgoals.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.smartgoals.R
import com.android.smartgoals.databinding.ComponentTextBinding

class HintExampleRecyclerViewAdapter(private val textList: List<String>):
    RecyclerView.Adapter<HintExampleRecyclerViewAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ComponentTextBinding): RecyclerView.ViewHolder(binding.root) {
        fun onBind(text: String) {
            binding.textViewHintExample.text = text
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ComponentTextBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemText = textList[position]
        holder.onBind(itemText)
    }

    override fun getItemCount(): Int {
        return textList.size
    }
}