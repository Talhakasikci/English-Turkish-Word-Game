package com.talhakasikci.wordapp

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.talhakasikci.wordapp.databinding.ActivityMainBinding
import com.talhakasikci.wordapp.ui.WordListViewModel

class MainActivity : AppCompatActivity() {
    private val viewModel: WordListViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = (supportFragmentManager
            .findFragmentById(R.id.fragmentContainerView) as NavHostFragment).navController
        setupBottomNavigation(navController)
    }

    private fun setupBottomNavigation(navController: NavController) {
        binding.bottomNavigationBar.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.wordsFragment -> {
                    handleWordsNavigation(navController)
                    true
                }
                R.id.mainFragment -> {
                    handleQuizNavigation(navController)
                    true
                }
                R.id.favFragment -> {
                    handleFavoritesNavigation(navController)
                    true
                }
                else -> false
            }
        }
    }

    private fun handleWordsNavigation(navController: NavController) {
        viewModel.setNavigationForPractise()

        if (viewModel.needLevelSelection.value == true) {
            navController.navigate(R.id.mainFragment,
                Bundle().apply {
                    putInt("targetID", R.id.wordsFragment)
                })
        } else if (viewModel.canNavigateToWords()) {
            navController.navigate(R.id.wordsFragment,
                Bundle().apply {
                    putString("EnglishLevel", viewModel.selectedLevel.value ?: "")
                    putString("StartingLetter", viewModel.selectedLetter.value ?: "")
                    putString("mode", "practice")
                })
        } else {
            navController.navigate(R.id.mainFragment,
                Bundle().apply {
                    putInt("targetID", R.id.wordsFragment)
                })
        }
    }

    private fun handleQuizNavigation(navController: NavController) {
        viewModel.setNavigationForQuiz()

        if (viewModel.needLevelSelection.value == true) {
            navController.navigate(R.id.mainFragment,
                Bundle().apply {
                    putInt("targetID", R.id.quizFragment)
                })
        } else if (viewModel.canNavigateToQuiz()) {
            navController.navigate(R.id.quizFragment,
                Bundle().apply {
                    putString("level", viewModel.selectedLevel.value ?: "")
                    putString("letter", viewModel.selectedLetter.value ?: "")
                })
        } else {
            navController.navigate(R.id.mainFragment,
                Bundle().apply {
                    putInt("targetID", R.id.quizFragment)
                })
        }
    }

    private fun handleFavoritesNavigation(navController: NavController) {
        viewModel.setnavigationForFavorites()

        navController.navigate(R.id.wordsFragment,
            Bundle().apply {
                putString("mode", "fav")
                putString("EnglishLevel", "")
                putString("StartingLetter", "")
            })
    }
}