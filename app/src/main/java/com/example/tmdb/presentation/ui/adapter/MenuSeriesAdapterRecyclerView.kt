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
import com.example.tmdb.presentation.ui.activity.MovieDetails

import com.example.tmdb.presentation.ui.animation.LoadingIconAnimation

class MenuSeriesAdapterRecyclerView: RecyclerView.Adapter<MenuSerieViewholder>(){

    private val arrayOfSeries = ArrayList<Serie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuSerieViewholder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cd_serie_item_recycler, parent, false)

        return MenuSerieViewholder(view)
    }

    override fun getItemCount() = arrayOfSeries.size

    override fun onBindViewHolder(holder: MenuSerieViewholder, position: Int) {
        holder.bind(arrayOfSeries[position])
    }

    fun setList(serie: List<Serie>) {
        arrayOfSeries.clear()
        arrayOfSeries.addAll(serie)
        notifyDataSetChanged()
    }
}

class MenuSerieViewholder(itemView: View): RecyclerView.ViewHolder(itemView){

    fun bind(serie: Serie) {
        val imageSerie = itemView.findViewById<ImageView>(R.id.imvSeriePosterPath)

        val loadingProgressBar = itemView.findViewById<ProgressBar>(R.id.loadingAnimation)
        loadingProgressBar.visibility = View.VISIBLE

        Glide.with(itemView.context)
            .load("http://image.tmdb.org/t/p/original/" + serie.poster)
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
            dados.putString("instant", serie.instant)
            dados.putString("title", serie.title)
            dados.putString("poster", serie.poster)
            dados.putString("details", serie.details)
            dados.putString("posterWide",serie.posterWide)
            dados.putParcelable("seriesDetails", serie)

            intent.putExtras(dados)

            itemView.context.startActivity(intent, activityOptionsCompat.toBundle())

        }
    }
}