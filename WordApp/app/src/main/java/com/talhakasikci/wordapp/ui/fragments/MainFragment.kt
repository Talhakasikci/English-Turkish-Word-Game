package com.talhakasikci.wordapp.ui.fragments

import android.os.Bundle
import android.text.Layout.Directions
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.talhakasikci.wordapp.R
import com.talhakasikci.wordapp.databinding.FragmentMainBinding
import com.talhakasikci.wordapp.ui.WordListViewModel

class MainFragment : Fragment() {
    private val viewModel : WordListViewModel by viewModels()
    private lateinit var binding: FragmentMainBinding
    lateinit var level: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            A1Button.setOnClickListener { level = "A1"
                actionFragment(level) }
            A2Button.setOnClickListener { level = "A2"
                actionFragment(level) }
            B1Button.setOnClickListener { level = "B1"
                actionFragment(level) }
            B2Button.setOnClickListener { level = "B2"
                actionFragment(level) }
        }

//        viewModel.words.observe(viewLifecycleOwner){list ->
//            list.forEach { word ->
//                if(word.level == "A1"){
//                    Log.d("WordEntry", "Word: ${word.word}, Turkish Meaning: ${word.meaning}, Level: ${word.level}")
//                }
//
//            }
//        }
    }
    
    private fun actionFragment(level:String){
        val action = MainFragmentDirections.actionMainFragmentToWordsFragment(level)
        findNavController().navigate(action)
    }


}