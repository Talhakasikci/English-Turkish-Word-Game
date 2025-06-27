package com.talhakasikci.wordapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.talhakasikci.wordapp.R
import com.talhakasikci.wordapp.adapter.LetterAdapter
import com.talhakasikci.wordapp.adapter.WordAdapter
import com.talhakasikci.wordapp.databinding.FragmentLetterBinding


class LetterFragment : Fragment() {
    private lateinit var binding: FragmentLetterBinding
    private val alphabet = ('A'..'Z').map { it.toString() }
    private val args: LetterFragmentArgs by navArgs()
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

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val level = args.EnglishLevel
        val adapter = LetterAdapter(alphabet) { letter ->
            val action = LetterFragmentDirections
                .actionLetterFragmentToWordsFragment(
                    EnglishLevel = level,
                    StartingLetter = letter
                )
            findNavController().navigate(action)
        }

        binding.LetterRV.apply {
            layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL, false)
            this.adapter = adapter  // ← burası önemli
        }
    }

    }


