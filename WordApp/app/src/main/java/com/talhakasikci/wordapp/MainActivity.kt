package com.talhakasikci.wordapp

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.LifecycleOwner
import com.talhakasikci.wordapp.ui.WordListViewModel

class MainActivity : AppCompatActivity() {
    private val viewModel: WordListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        viewModel.words.observe(this){list ->
            list.forEach { word ->
                if(word.level == "A1"){
                    Log.d("WordEntry", "Word: ${word.word}, Turkish Meaning: ${word.meaning}, Level: ${word.level}")
                }

            }
        }
    }
}