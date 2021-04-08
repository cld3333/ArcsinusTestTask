package ru.surfstudio.standard.f_characters.search.controllers

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import ru.surfstudio.android.easyadapter.controller.BindableItemController
import ru.surfstudio.android.easyadapter.holder.BindableViewHolder
import ru.surfstudio.android.imageloader.ImageLoader
import ru.surfstudio.standard.domain.characters.Character
import ru.surfstudio.standard.f_characters.R

class CharacterController() :
        BindableItemController<Character, CharacterController.Holder>() {

    override fun getItemId(data: Character): String = data.hashCode().toString()

    override fun createViewHolder(parent: ViewGroup): Holder = Holder(parent)

    inner class Holder(
            parent: ViewGroup,
    ) : BindableViewHolder<Character>(parent, R.layout.item_character) {

        private val nameTv = itemView.findViewById<TextView>(R.id.name_tv)
        private val descriptionTv = itemView.findViewById<TextView>(R.id.description_tv)
        private val imageIv = itemView.findViewById<ImageView>(R.id.character_image_iv)

        override fun bind(data: Character) {
            nameTv.text = data.name
            descriptionTv.text = data.description
            ImageLoader.with(imageIv.context)
                    .url(data.imageUrl)
                    .preview(R.drawable.ic_android)
                    .into(imageIv)
        }
    }
}