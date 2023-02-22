package com.android.smartgoals.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.android.smartgoals.databinding.MeasurableSpinnerItemBinding.*
import com.android.smartgoals.models.MeasurableSpinnerItem

class SpinnerAdapter(private val listOfSpinnerItems: List<MeasurableSpinnerItem>) :
    BaseAdapter() {
    override fun getCount(): Int {
        return listOfSpinnerItems.size
    }

    override fun getItem(p0: Int): Any? {
        return null
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        val binding = inflate(LayoutInflater.from(parent?.context), parent, false)
        val spinnerItem = listOfSpinnerItems[position]
        binding.spinnerIcon.setImageResource(spinnerItem.icon)
        binding.textViewSpinnerName.text = spinnerItem.type
        return binding.root
    }
}

