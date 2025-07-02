package com.talhakasikci.wordapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.talhakasikci.wordapp.R
import com.talhakasikci.wordapp.data.McQuestions
import com.talhakasikci.wordapp.data.WordEntry
import com.talhakasikci.wordapp.data.WordRepository
import com.talhakasikci.wordapp.databinding.FragmentQuizBinding
import com.talhakasikci.wordapp.ui.WordListViewModel


class QuizFragment : Fragment() {

    private lateinit var binding: FragmentQuizBinding
    private val viewModel: WordListViewModel by viewModels()
    private var wordList: List<WordEntry> = emptyList()
    private var currentIndex = 0
    private lateinit var question: List<McQuestions>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentQuizBinding.inflate(layoutInflater, container, false)
        viewModel.words.observe(viewLifecycleOwner) {
            wordList = it
            question = generateQuiz(wordList, 10)
            currentIndex = 0
            showQuestion()
            setUpAnswerListeners()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    private fun showQuestion() {
        if (question.isEmpty()) return
        val q = question[currentIndex]
        binding.apply {
            QuestionWordTV.text = q.englishWord
            Answer1TV.text = q.options[0]
            Answer2TV.text = q.options[1]
            Answer3TV.text = q.options[2]
            Answer4TV.text = q.options[3]
        }
    }

    fun generateWrongOptions(
        correct: WordEntry,
        pool: List<WordEntry>,
        count: Int = 3
    ): List<String> {
        val sameLevel = pool.filter {
            it.level == correct.level && it.word != correct.word
        }.shuffled()//karıştırmak için

        val options = sameLevel.take(count).map { it.meaning }

        return if (options.size < count) {
            options + pool
                .filter {
                    it.word != correct.word && it.meaning !in options
                }
                .shuffled()
                .take(count - options.size)
                .map { it.meaning }
        } else {
            options
        }
    }

    fun generateQuiz(
        pool: List<WordEntry>,
        count: Int
    ): List<McQuestions> {
        return pool.shuffled()
            .take(count)
            .map { entry ->
                val wrongs = generateWrongOptions(entry, pool, 3)
                val opts = (wrongs + entry.meaning).shuffled()
                McQuestions(
                    englishWord = entry.word,
                    correctMeaning = entry.meaning,
                    options = opts,
                    level = entry.level
                )
            }
    }

    private fun setUpAnswerListeners() {
        listOf(
            binding.Answer1TV to 0,
            binding.Answer2TV to 1,
            binding.Answer3TV to 2,
            binding.Answer4TV to 3
        ).forEach { (card, idx) ->
            card.setOnClickListener {
                checkAnswer(idx)
            }
        }
    }


    fun checkAnswer(idx: Int) {
        val q = question[currentIndex]
        val choosen = q.options[idx]
        if (choosen == q.correctMeaning) {
            //doğru
            currentIndex++
            if (currentIndex < question.size) {
                showQuestion()
            } else {
                //yanlış
            }
        }

    }

}