package com.talhakasikci.wordapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.talhakasikci.wordapp.R
import com.talhakasikci.wordapp.adapter.WordAdapter
import com.talhakasikci.wordapp.data.WordEntry
import com.talhakasikci.wordapp.databinding.FragmentWordsBinding
import com.talhakasikci.wordapp.ui.WordListViewModel


class wordsFragment : Fragment() {
    private val args: wordsFragmentArgs by navArgs()
    private val viewModel : WordListViewModel by viewModels()
    private lateinit var WordArraytList: ArrayList<WordEntry>
    private lateinit var binding : FragmentWordsBinding
    private lateinit var RV :RecyclerView
    private lateinit var adapter : WordAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WordArraytList = ArrayList()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentWordsBinding.inflate(inflater, container, false)
        val level = args.EnglishLevel
        binding.level.text = "${getString(R.string.LevelDescription)}: ${level.toString()}"
//
//        viewModel.words.observe(viewLifecycleOwner) { list ->
//            WordArraytList = ArrayList()
//            list.forEach { word ->
//                if (word.level == level) {
//                    WordArraytList.add(word)
//                }
//            }
//
//
//        }
        adapter = WordAdapter(WordArraytList)
        binding.wordsRV.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@wordsFragment.adapter
        }
        viewModel.words.observe(viewLifecycleOwner) { list ->
            // filtrele
            WordArraytList.clear()
            list.filter { it.level == level }.also { WordArraytList.addAll(it) }

            // sonra adapter’ı güncelle
            adapter.notifyDataSetChanged()
        }

        return binding.root
    }


}