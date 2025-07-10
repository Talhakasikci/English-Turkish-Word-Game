package com.talhakasikci.wordapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.talhakasikci.wordapp.R
import com.talhakasikci.wordapp.adapter.LetterAdapter
import com.talhakasikci.wordapp.adapter.WordAdapter
import com.talhakasikci.wordapp.databinding.FragmentLetterBinding
import com.talhakasikci.wordapp.ui.WordListViewModel


class LetterFragment : Fragment() {
    private lateinit var binding: FragmentLetterBinding
    private val alphabet = ('A'..'Z').map { it.toString() }
    private val section1 = ('A'..'D').map { it.toString() }
    private val section2 = ('E'..'H').map { it.toString() }
    private val section3 = ('I'..'M').map { it.toString() }
    private val section4 = ('N'..'Q').map { it.toString() }
    private val section5 = ('R'..'U').map { it.toString() }
    private val section6 = ('V'..'Z').map { it.toString() }
    private val viewModel: WordListViewModel by viewModels()
    private val args: LetterFragmentArgs by navArgs()
    private val target: Int by lazy{ args.targetID }
    private val level: String by lazy{ args.EnglishLevel }

    private var list: List<String> = emptyList()
    //private lateinit var adapter : LetterAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLetterBinding.inflate(inflater, container, false)
        chooseLetter()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = LetterAdapter(list){selectedLetter->
            viewModel.selectedLetter(selectedLetter)
            when(target){
                R.id.quizFragment->{
                    val action = LetterFragmentDirections.actionLetterFragmentToQuizFragment(level = level, letter = selectedLetter)
                    findNavController().navigate(action)
                }
                R.id.wordsFragment->{
                    val action = LetterFragmentDirections.actionLetterFragmentToWordsFragment(EnglishLevel = level , StartingLetter = selectedLetter, mode = "")
                    findNavController().navigate(action)
                }
            }
        }


//        val adapter = LetterAdapter(alphabet) { displaytext ->
//            if(target == R.id.quizFragment) {
//                val group = displaytext.split(", ")
//
//            }
//        }

        binding.LetterRV.apply {
            layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL, false)
            this.adapter = adapter  //
        }
    }
    private fun chooseLetter(){

       list = if(target == R.id.quizFragment){
           listOf(
               section1.joinToString(", "),
               section2.joinToString(", "),
               section3.joinToString(", "),
               section4.joinToString(", "),
               section5.joinToString(", "),
               section6.joinToString(", ")

           )
       } else {
              alphabet
         }


    }

}



