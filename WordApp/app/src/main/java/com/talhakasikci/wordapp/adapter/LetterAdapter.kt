package com.talhakasikci.wordapp.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.talhakasikci.wordapp.databinding.LetterItemRowRvBinding

class LetterAdapter(val alphabet:List<String>
                    ,private val onClick: (String) -> Unit
                        ): RecyclerView.Adapter<LetterAdapter.LetterViewHolder>() {


    class LetterViewHolder(val binding:LetterItemRowRvBinding) : RecyclerView.ViewHolder(binding.root) {
        // You can add any additional setup for the ViewHolder here if needed
        fun bind(letter: String) {
            binding.letterTV.text = letter
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LetterViewHolder {
        val binding = LetterItemRowRvBinding.inflate(
            android.view.LayoutInflater.from(parent.context), parent, false
        )
        return LetterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LetterViewHolder, position: Int) {
        val letter = alphabet[position]
        holder.binding.apply {
            letterTV.text = "Words With Starts: $letter"
        }
        holder.bind(letter)
        holder.itemView.setOnClickListener {
            onClick(letter)
        }
    }

    override fun getItemCount(): Int {
        return alphabet.size
    }


}