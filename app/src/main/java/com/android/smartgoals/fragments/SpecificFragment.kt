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
import com.android.smartgoals.databinding.FragmentSpecificBinding
import com.android.smartgoals.viewmodels.ButtonState
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
//                if(state == ButtonState.CONFIRM) {
//                    binding.buttonAddGoal.text = "Confirm"
//                    binding.buttonAddGoal.isEnabled = false
//                    binding.textInputGoal.visibility = View.VISIBLE
//
//                    binding.textInputEditTextGoal.addTextChangedListener {
//                        binding.buttonAddGoal.isEnabled =
//                            binding.textInputEditTextGoal.text.toString().isNotEmpty()
//                    }
//                } else { //default state, ready to answer
//
//                }
            }
        }
    }

    private fun setupOnClickListeners() {
        binding.cardViewHint.setOnClickListener {
            viewModel.onExpandedHint()
        }

        binding.cardViewExample.setOnClickListener {
            viewModel.onExpandedExample()
        }
    }

    private fun whatButtonDo(buttonState: ButtonState) {
        when(buttonState) {
            ButtonState.READYTOANSWER -> {
                binding.buttonAddGoal.text = "Confirm"
//                binding.buttonAddGoal.isEnabled = false
                binding.textInputGoal.visibility = View.VISIBLE

                if (!activityViewModel.specificGoalComponent.value.completed) {
                    Log.d("SpecificComponent", "!activityViewModel.specificGoalComponent.value.completed")
                    binding.textInputEditTextGoal.addTextChangedListener {
                        binding.buttonAddGoal.isEnabled =
                            binding.textInputEditTextGoal.text.toString().isNotEmpty()
                    }
                } else {
                    Log.d("SpecificComponent", "else of !activityViewModel.specificGoalComponent.value.completed")
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