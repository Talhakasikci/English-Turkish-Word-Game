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
import androidx.navigation.fragment.navArgs
import com.talhakasikci.wordapp.R
import com.talhakasikci.wordapp.databinding.FragmentMainBinding
import com.talhakasikci.wordapp.ui.WordListViewModel

class MainFragment : Fragment() {
    private val viewModel : WordListViewModel by viewModels()
    private lateinit var binding: FragmentMainBinding
    lateinit var level: String
    private val args: MainFragmentArgs by navArgs()

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

        val buttons = listOf(
            binding.A1Button to "A1",
            binding.A2Button to "A2",
            binding.B1Button to "B1",
            binding.B2Button to "B2"
        )

        buttons.forEach { (btn,level)->  // action fragment fonksiyonunu 1 kere yazmak için bunu kullandım
            btn.setOnClickListener {
                actionFragment(level)
            }
        }

//        binding.apply {
//            A1Button.setOnClickListener { level = "A1"
//                actionFragment(level)
//                 }
//            A2Button.setOnClickListener { level = "A2"
//                actionFragment(level)
//                 }
//            B1Button.setOnClickListener { level = "B1"
//                actionFragment(level)
//                }
//            B2Button.setOnClickListener { level = "B2"
//                actionFragment(level)
//                }
//        }
        //eski yöntem


    }
    // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    private fun actionFragment(level:String){
        val target = args.targetID
        if(target == R.id.wordsFragment){
            val action = MainFragmentDirections.actionMainFragmentToLetterFragment(EnglishLevel = level, targetID = target)
            findNavController().navigate(action)
        } else if(args.targetID == R.id.quizFragment) {
            val action = MainFragmentDirections.actionMainFragmentToLetterFragment(EnglishLevel = level, targetID = target)
            findNavController().navigate(action)
        }
    }// hata !!!!!!!!!!!!!!!


}