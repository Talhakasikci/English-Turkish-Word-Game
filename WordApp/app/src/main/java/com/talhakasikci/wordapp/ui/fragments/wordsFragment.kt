package com.talhakasikci.wordapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.talhakasikci.wordapp.R
import com.talhakasikci.wordapp.adapter.WordAdapter
import com.talhakasikci.wordapp.data.WordEntry
import com.talhakasikci.wordapp.databinding.FragmentWordsBinding
import com.talhakasikci.wordapp.ui.WordListViewModel

class wordsFragment : Fragment(R.layout.fragment_words) {

    private val args: wordsFragmentArgs by navArgs()
    private val viewModel: WordListViewModel by viewModels()
    private var _binding: FragmentWordsBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: WordAdapter
    private val wordList = ArrayList<WordEntry>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentWordsBinding.bind(view)
        initRecyclerView()
        initObservers()
    }

    private fun initRecyclerView() {
        adapter = WordAdapter(wordList) { entry ->
            viewModel.toggleFavorite(entry)
        }
        binding.wordsRV.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@wordsFragment.adapter
        }
    }

    private fun initObservers() {
        val mode = when{
            args.mode.isNotEmpty()->args.mode
            viewModel.currentMode.value == "fav"->"fav"
            else->"practise"
        }

        when(mode){
            "fav"->observeFavorites()
            else-> observeFiltered()
        }
    }

    private fun observeFavorites() {
        binding.level.text = getString(R.string.Fav_Words_Button)
        viewModel.favoriteSet.observe(viewLifecycleOwner){favSet->
            adapter.favoriteSet = favSet

        }
        viewModel.favoriteWords.observe(viewLifecycleOwner) { list ->
            updateList(list)
        }
    }

    private fun observeFiltered() {
        val level = when{
            args.EnglishLevel.isNotEmpty()->args.EnglishLevel
            else -> viewModel.selectedLevel.value?:""
        }
        val letter = when{
            args.StartingLetter.isNotEmpty()->args.StartingLetter
            else-> viewModel.selectedLevel.value?:""
        }
        binding.level.text = getString(R.string.LevelDescription)+ ": $level"

        viewModel.favoriteSet.observe(viewLifecycleOwner) { favSet ->
            adapter.favoriteSet = favSet
        }
        viewModel.words.observe(viewLifecycleOwner) { all ->
            val filteredByLevel = filterByLevel(all, args.EnglishLevel)
            val filteredByLetter = filterByLetter(filteredByLevel, args.StartingLetter)
            updateList(filteredByLetter)
        }
    }

    private fun filterByLevel(list: List<WordEntry>, level: String) =
        if (level.isNotEmpty()) list.filter { it.level == level } else list

    private fun filterByLetter(list: List<WordEntry>, letter: String) =
        if (letter.isNotEmpty()) list.filter { it.word.startsWith(letter, ignoreCase = true) } else list

    private fun updateList(newList: List<WordEntry>) {
        wordList.clear()
        wordList.addAll(newList)
        adapter.notifyDataSetChanged()
        toggleEmptyView(newList.isEmpty())
    }

    private fun toggleEmptyView(isEmpty: Boolean) {
        binding.apply {
            emptyView.visibility   = if (isEmpty) View.VISIBLE else View.GONE
            wordsRV.visibility     = if (isEmpty) View.GONE    else View.VISIBLE
            progressBar.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
