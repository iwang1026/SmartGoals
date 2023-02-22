package com.android.smartgoals.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.smartgoals.R
import com.android.smartgoals.adapters.HintExampleRecyclerViewAdapter
import com.android.smartgoals.adapters.SpinnerAdapter
import com.android.smartgoals.databinding.FragmentMeasurableBinding
import com.android.smartgoals.models.MeasurableSpinnerItem
import com.android.smartgoals.viewmodels.MainViewModel
import com.android.smartgoals.viewmodels.MeasurableViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MeasurableFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private var _binding: FragmentMeasurableBinding? = null
    private val binding get() = _binding!!

    private val viewModel = MeasurableViewModel()
    private val activityViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMeasurableBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewModel.expandedHint.collectLatest { isExpanded ->
                binding.hintExpanded = isExpanded
            }
        }

        lifecycleScope.launch {
            viewModel.expandedExample.collectLatest { isExpanded ->
                binding.exampleExpanded = isExpanded
            }
        }

        binding.includedMeasurable.cardViewHint.setOnClickListener {
            viewModel.onExpandedHint()
        }

        binding.includedMeasurable.cardViewExample.setOnClickListener {
            viewModel.onExpandedExample()
        }

        binding.buttonAddGoal.setOnClickListener {
            binding.isReadyToAnswer = true
        }

        binding.includedMeasurable.recyclerViewHint.layoutManager = LinearLayoutManager(context)
        binding.includedMeasurable.recyclerViewHint.adapter = HintExampleRecyclerViewAdapter(
            listOf(
                resources.getString(R.string.component_measurable_hint_2),
                resources.getString(R.string.component_measurable_hint_3),
                resources.getString(R.string.component_measurable_hint_4)
            )
        )

        binding.includedMeasurable.recyclerViewExample.layoutManager = LinearLayoutManager(context)
        binding.includedMeasurable.recyclerViewExample.adapter = HintExampleRecyclerViewAdapter(
            listOf(
                resources.getString(R.string.component_measurable_example_1),
                resources.getString(R.string.component_measurable_example_2),
                resources.getString(R.string.component_measurable_example_3)
            )
        )

//        val arrayAdapter = ArrayAdapter(
//            requireContext(),
//            android.R.layout.simple_spinner_item,
//            listOf(
//                resources.getString(R.string.component_measurable_metric_1),
//                resources.getString(R.string.component_measurable_metric_2),
//                resources.getString(R.string.component_measurable_metric_3),
//                resources.getString(R.string.component_measurable_metric_4)
//            )
//        )
//
//        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val listOfSpinnerOptions = listOf(
            MeasurableSpinnerItem(R.drawable.ic_frequency, resources.getString(R.string.component_measurable_metric_1)),
            MeasurableSpinnerItem(R.drawable.ic_target, resources.getString(R.string.component_measurable_metric_2)),
            MeasurableSpinnerItem(R.drawable.ic_count, resources.getString(R.string.component_measurable_metric_3)),
            MeasurableSpinnerItem(R.drawable.ic_other_measure, resources.getString(R.string.component_measurable_metric_4))
        )
        binding.spinnerMeasurementType.adapter = SpinnerAdapter(listOfSpinnerOptions)
        binding.spinnerMeasurementType.onItemSelectedListener = this
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    //TODO come back to this, is this even needed?
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when(position) {
            1 -> Log.d("MeasurableComponent", "onItemSelected(), view.id = 1, position $position, id $id, view $view")
            else -> {
                Log.d("MeasurableComponent", "onItemSelected(), else of view.id = 1, position $position, id $id, view $view")
            }
        }

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        Log.d("MeasurableComponent", "onNothingSelected()")
    }

}