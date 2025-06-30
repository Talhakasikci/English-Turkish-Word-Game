package com.talhakasikci.wordapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.talhakasikci.wordapp.R
import com.talhakasikci.wordapp.data.WordEntry
import com.talhakasikci.wordapp.databinding.WordsItemRowRvBinding
import com.talhakasikci.wordapp.ui.WordListViewModel

class WordAdapter(private val WordsArrayList: ArrayList<WordEntry>,
    private val onFavClick:(WordEntry)->Unit): RecyclerView.Adapter<WordAdapter.WordViewHolder>()  {

        var favoriteSet: Set<String> = emptySet()
            set(value){
                field = value
                notifyDataSetChanged()
            }


    class WordViewHolder(val binding: WordsItemRowRvBinding) : RecyclerView.ViewHolder(binding.root) {
        // You can add any additional setup for the ViewHolder here if needed
        fun bind(wordEntry: WordEntry) {
            binding.englishWord.text = wordEntry.word
            binding.turkishWord.text = wordEntry.meaning
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
       val binding = WordsItemRowRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WordViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val entry = WordsArrayList[position]
        val isFav = favoriteSet.contains(entry.word)
        holder.binding.apply {
            englishWord.text = entry.word
            turkishWord.text = entry.meaning

            favButton.setIconResource(
                if (isFav) R.drawable.filled_star
                else R.drawable.add_favorite
            )

            favButton.setOnClickListener {
                onFavClick(entry)
            }
        }

    }

    override fun getItemCount(): Int {
        return WordsArrayList.size
    }


}