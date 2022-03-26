package com.droidli.marvel.util

import androidx.recyclerview.widget.DiffUtil
import com.droidli.marvel.domain.model.Character

class GridDiffUtil : DiffUtil.ItemCallback<Character>() {
    override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
        return oldItem == newItem
    }
}