package com.android.smartgoals.fragments

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.android.smartgoals.R
import com.android.smartgoals.databinding.FragmentAddGoalWizardBinding
import com.android.smartgoals.viewmodels.MainViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AddGoalWizardFragment : Fragment() {

    private var _binding: FragmentAddGoalWizardBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddGoalWizardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonAddGoal.setOnClickListener {
            Toast.makeText(context, "button add goal clicked", Toast.LENGTH_SHORT).show()
        }
        binding.specificOnClick = View.OnClickListener { findNavController().navigate(R.id.specificFragment) }

        lifecycleScope.launch {
            mainViewModel.specificGoalComponent.collectLatest { component ->
                Log.d("Debugging", "component description ${component.description}")
                    binding.specificShouldShow = component.completed
                    binding.specificDescription = component.description
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}