package com.example.tmdb.presentation.ui.adapter

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tmdb.R
import com.example.tmdb.data.repository.remote.entity.Serie
import com.example.tmdb.domain.Media
import com.example.tmdb.presentation.ui.activity.MovieDetails

import com.example.tmdb.presentation.ui.animation.LoadingIconAnimation

class MenuSeriesAdapterRecyclerView: RecyclerView.Adapter<MenuSerieViewholder>(){

    private val arrayOfMedias = ArrayList<Media>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuSerieViewholder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cd_serie_item_recycler, parent, false)

        return MenuSerieViewholder(view)
    }

    override fun getItemCount() = arrayOfMedias.size

    override fun onBindViewHolder(holder: MenuSerieViewholder, position: Int) {
        holder.bind(arrayOfMedias[position])
    }

    fun setList(media: List<Media>) {
        arrayOfMedias.clear()
        arrayOfMedias.addAll(media)
        notifyDataSetChanged()
    }
}

class MenuSerieViewholder(itemView: View): RecyclerView.ViewHolder(itemView){

    fun bind(media: Media) {
        val imageSerie = itemView.findViewById<ImageView>(R.id.imvSeriePosterPath)

        val loadingProgressBar = itemView.findViewById<ProgressBar>(R.id.loadingAnimation)
        loadingProgressBar.visibility = View.VISIBLE

        Glide.with(itemView.context)
            .load("http://image.tmdb.org/t/p/original/" + media.poster)
            .centerCrop()
            .listener(
                LoadingIconAnimation(
                    loadingProgressBar
                )
            )
            .into(imageSerie)

        itemView.setOnClickListener {
            val intent = Intent(itemView.context , MovieDetails::class.java)

            //Animation
            val activityOptionsCompat =
                ActivityOptionsCompat
                    .makeSceneTransitionAnimation(itemView.context as Activity,
                        imageSerie, "posterSerie")

            //Dados
            val dados = Bundle()
            dados.putString("instant", media.instant)
            dados.putString("title", media.title)
            dados.putString("poster", media.poster)
            dados.putString("details", media.details)
            dados.putString("posterWide",media.posterWide)
            dados.putParcelable("mediaDetails", media)

            intent.putExtras(dados)

            itemView.context.startActivity(intent, activityOptionsCompat.toBundle())

        }
    }
}