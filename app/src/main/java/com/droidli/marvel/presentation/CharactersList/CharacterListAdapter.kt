package com.droidli.marvel.presentation.CharactersList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.droidli.marvel.databinding.GridLayoutBinding
import com.droidli.marvel.util.GridDiffUtil

class CharacterListAdapter() :
    RecyclerView.Adapter<CharacterListAdapter.CharacterListViewHolder>() {

    inner class CharacterListViewHolder(val binding: GridLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    val differ = AsyncListDiffer(this, GridDiffUtil())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterListViewHolder {
        return CharacterListViewHolder(
            GridLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CharacterListViewHolder, position: Int) {
        val list = differ.currentList[position]
        holder.binding.apply {
            txtCharacterName.text = list.name
            val imageUrl = "${list.thumbnail}/portrait_xlarge.${list.thumbnailExt}"
            Glide.with(root).load(imageUrl).into(imgCharacterImage)
            cardView.setOnClickListener {

            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}