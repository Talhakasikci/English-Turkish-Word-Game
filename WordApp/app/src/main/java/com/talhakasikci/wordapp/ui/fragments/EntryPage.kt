package com.talhakasikci.wordapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.talhakasikci.wordapp.R
import com.talhakasikci.wordapp.databinding.FragmentEntryPageBinding


class EntryPage : Fragment() {

    private lateinit var binding: FragmentEntryPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEntryPageBinding.inflate(inflater, container, false)


        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.PractiseWordsCV.setOnClickListener {
            val action = EntryPageDirections.actionEntryPageToMainFragment(R.id.wordsFragment)
            requireView().findNavController().navigate(action)
        }

        binding.FavWordsCV.setOnClickListener {
            val action = EntryPageDirections.actionEntryPageToWordsFragment(
                mode = "fav"
            )
            requireView().findNavController().navigate(action)
        }

        binding.QuizWordsCV.setOnClickListener {
            val action = EntryPageDirections.actionEntryPageToMainFragment(R.id.quizFragment)
            requireView().findNavController().navigate(action)
        }
    }


}