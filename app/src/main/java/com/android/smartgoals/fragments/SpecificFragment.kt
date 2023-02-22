package com.android.smartgoals.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.smartgoals.R
import com.android.smartgoals.adapters.HintExampleRecyclerViewAdapter
import com.android.smartgoals.databinding.FragmentSpecificBinding
import com.android.smartgoals.models.ButtonState
import com.android.smartgoals.viewmodels.MainViewModel
import com.android.smartgoals.viewmodels.SpecificViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SpecificFragment : Fragment() {

    private var _binding: FragmentSpecificBinding? = null
    private val binding get() = _binding!!

    private val viewModel = SpecificViewModel()
    private val activityViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSpecificBinding.inflate(inflater, container, false)
        Log.d("SpecificComponent", "onCreateView")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("SpecificComponent", "onViewCreated")
        setupOnClickListeners()
        setupRecyclerViews()

        val goalComponentState = activityViewModel.specificGoalComponent.value

        if (goalComponentState.completed) {
            viewModel.onButtonClicked(ButtonState.READYTOANSWER)
        }
        binding.textInputEditTextGoal.setText(goalComponentState.description, TextView.BufferType.EDITABLE)

        lifecycleScope.launch {
            viewModel.expandedHint.collectLatest { isExpanded->
                binding.hintExpanded = isExpanded
            }
        }

        lifecycleScope.launch {
            viewModel.expandedExample.collectLatest { isExpanded ->
//                val contextWrapper = ContextThemeWrapper(requireContext(), R.style.CustomCardViewStyleExpanded)
//                val card = MaterialCardView(contextWrapper)

                //TODO can't change the style programmatically to use a different style when expanded or not
                binding.exampleExpanded = isExpanded
            }
        }

        binding.buttonAddGoal.setOnClickListener {
            val state: ButtonState = when(binding.buttonAddGoal.text) {
                "Confirm" -> ButtonState.CONFIRM
                else -> ButtonState.READYTOANSWER
            }
            viewModel.onButtonClicked(state)
        }

        lifecycleScope.launch {
            viewModel.buttonState.collectLatest { state ->
                whatButtonDo(state)
            }
        }
    }

    private fun setupRecyclerViews() {
        binding.includedSpecific.recyclerViewHint.layoutManager = LinearLayoutManager(context)
        binding.includedSpecific.recyclerViewHint.adapter = HintExampleRecyclerViewAdapter(
            listOf(
                resources.getString(R.string.component_specific_hint_2),
                resources.getString(R.string.component_specific_hint_3),
                resources.getString(R.string.component_specific_hint_4),
                resources.getString(R.string.component_specific_hint_5),
                resources.getString(R.string.component_specific_hint_6),
            )
        )

        binding.includedSpecific.recyclerViewExample.layoutManager = LinearLayoutManager(context)
        binding.includedSpecific.recyclerViewExample.adapter = HintExampleRecyclerViewAdapter(
            listOf(
                resources.getString(R.string.component_specific_example_1),
                resources.getString(R.string.component_specific_example_2),
                resources.getString(R.string.component_specific_example_3)
            )
        )
    }

    private fun setupOnClickListeners() {
        binding.includedSpecific.cardViewHint.setOnClickListener {
            viewModel.onExpandedHint()
        }

        binding.includedSpecific.cardViewExample.setOnClickListener {
            viewModel.onExpandedExample()
        }
    }

    //TODO need to move this to VM, doing too much, need to figure out how
    private fun whatButtonDo(buttonState: ButtonState) { //TODO change name of this
        when(buttonState) {
            ButtonState.READYTOANSWER -> {
                binding.buttonAddGoal.text = "Confirm" //this remains in fragment

                //TODO need to pass in the value.isNotEmpty in as the isReadyToAnswer variable
                binding.buttonAddGoal.isEnabled = activityViewModel.specificGoalComponent.value.description.isNotEmpty() //move this to VM, use isTextEmpty
                binding.textInputGoal.visibility = View.VISIBLE

                    Log.d("SpecificComponent", "!activityViewModel.specificGoalComponent.value.completed")
                    binding.textInputEditTextGoal.addTextChangedListener {
                        binding.buttonAddGoal.isEnabled =
//                            binding.textInputEditTextGoal.text.toString().isNotEmpty()
                        viewModel.isTextEmpty(binding.textInputEditTextGoal.toString())
                    }
            }
            ButtonState.CONFIRM -> {
                //TODO save to local storage
                activityViewModel.setSpecificState(true)
                activityViewModel.setSpecificGoalDescription(binding.textInputEditTextGoal.text.toString())
                activity?.onBackPressed()
            }
            ButtonState.INITIAL -> {}
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}