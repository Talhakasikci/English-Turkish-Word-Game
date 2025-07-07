package com.talhakasikci.wordapp.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia.DefaultTab.AlbumsTab.value
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.card.MaterialCardView
import com.talhakasikci.wordapp.R
import com.talhakasikci.wordapp.data.McQuestions
import com.talhakasikci.wordapp.data.WordEntry
import com.talhakasikci.wordapp.data.WordRepository
import com.talhakasikci.wordapp.databinding.FragmentQuizBinding
import com.talhakasikci.wordapp.ui.WordListViewModel
import java.lang.reflect.Array.set


class QuizFragment : Fragment() {

    private val args: QuizFragmentArgs by navArgs()
    private val level : String by lazy{args.level}
    private val letter : String by lazy{args.letter}
    private lateinit var binding: FragmentQuizBinding
    private val viewModel: WordListViewModel by viewModels()
    private var wordList: List<WordEntry> = emptyList()
    private var wordListSize : Int = 0
    private var currentIndex = 0
    private var correnctAnswer = 0
    private var wrongAnswer = 0
    private lateinit var question: List<McQuestions>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentQuizBinding.inflate(layoutInflater, container, false)

        viewModel.words.observe(viewLifecycleOwner) {
            wordList = it.filter { it.level == level  && letter.any { start->
                it.word.startsWith(start, ignoreCase = true)
            }}
            wordListSize = wordList.size
            question = generateQuiz(wordList, wordListSize)
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

        binding.wordCounterTV.text = "${currentIndex + 1} / ${question.size}"
        if (question.isEmpty()) return
        val q = question[currentIndex]
        isFav(q.englishWord)
        binding.apply {
            QuestionWordTV.text = q.englishWord
            Answer1TV.text = q.options[0]
            Answer2TV.text = q.options[1]
            Answer3TV.text = q.options[2]
            Answer4TV.text = q.options[3]
            addFavorite.setOnClickListener {
                viewModel.toggleFavorite(
                    WordEntry(
                        word = q.englishWord,
                        meaning = q.correctMeaning,
                        level = q.level
                    )
                )
            }
        }

    }

    private fun isFav(word:String) {
        viewModel.favoriteWords.observe(viewLifecycleOwner){fav->
            val isFav = fav.any { it.word == word }
            binding.addFavorite.setImageResource(
                if (isFav) R.drawable.filled_star else R.drawable.add_favorite
            )

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
        hideAnimation()
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
            binding.Answer1CV to 0,
            binding.Answer2CV to 1,
            binding.Answer3CV to 2,
            binding.Answer4CV to 3
        ).forEach { (card, idx) ->
            card.setOnClickListener {
                checkAnswer(card,idx)
            }
        }
        Log.e("QuizzFragment","letter: $letter")
    }


    fun checkAnswer(card:MaterialCardView,idx: Int) {

        val q = question[currentIndex]
        val choosen = q.options[idx]
        if (choosen == q.correctMeaning) {
            //doğru
            val green = ContextCompat.getColor(requireContext(), R.color.green_true)
            card.setCardBackgroundColor(green)

            Handler(Looper.getMainLooper()).postDelayed({
                resetCardBackground(card)
                currentIndex++
                correnctAnswer++
                if (currentIndex < question.size) {
                    showQuestion()
                } else {
                    // Quiz bitti
                }
            },500)


        }else{
            val green = ContextCompat.getColor(requireContext(), R.color.red_false)
            card.setCardBackgroundColor(green)

            Handler(Looper.getMainLooper()).postDelayed({
                resetCardBackground(card)
                currentIndex++
                wrongAnswer++
                if (currentIndex < question.size) {
                    showQuestion()
                } else {
                    // Quiz bitti
                }
            },500)
        }

    }

    private fun resetCardBackground(card: MaterialCardView) {
        val default = ContextCompat.getColor(requireContext(), R.color.cardViewColor)
        binding.Answer1CV.setCardBackgroundColor(default)
        binding.Answer2CV.setCardBackgroundColor(default)
        binding.Answer3CV.setCardBackgroundColor(default)
        binding.Answer4CV.setCardBackgroundColor(default)
    }

    fun hideAnimation(){
        binding.apply{
            binding.wordCardViewAnimation.visibility = View.GONE
            binding.Answer1LottieAnimationView.visibility = View.GONE
            binding.Answer2LottieAnimationView.visibility = View.GONE
            binding.Answer3LottieAnimationView.visibility = View.GONE
            binding.Answer4LottieAnimationView.visibility = View.GONE

            binding.addFavorite.visibility = View.VISIBLE
            binding.wordCounterTV.visibility = View.VISIBLE
            binding.QuestionWordTV.visibility = View.VISIBLE
            binding.Answer1TV.visibility = View.VISIBLE
            binding.Answer2TV.visibility = View.VISIBLE
            binding.Answer3TV.visibility = View.VISIBLE
            binding.Answer4TV.visibility = View.VISIBLE
        }
    }

}