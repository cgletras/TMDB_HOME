package com.example.tmdb.presentation.ui.adapter

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tmdb.R
import com.example.tmdb.RoomTMDBApplication
import com.example.tmdb.domain.Media
import com.example.tmdb.domain.MediaMapper
import com.example.tmdb.presentation.ui.activity.MovieDetails
import com.example.tmdb.presentation.ui.animation.LoadingIconAnimation
import com.google.android.material.snackbar.Snackbar

class SearchMovieAdapter : RecyclerView.Adapter<ViewHolder>() {

    private val arrayOfMedias = ArrayList<Media>()

    private var removedPosition:Int = 0
    private lateinit var removedItem: Media

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_search_recycler, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount() = arrayOfMedias.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(arrayOfMedias[position])
    }

    fun setList(medias: ArrayList<Media>) {
        arrayOfMedias.clear()
        arrayOfMedias.addAll(medias)
        notifyDataSetChanged()
    }

    fun removeItem(holder: RecyclerView.ViewHolder) {
        removedPosition = holder.adapterPosition
        removedItem = arrayOfMedias[holder.adapterPosition]

        arrayOfMedias.removeAt(holder.adapterPosition)
        notifyItemRemoved(holder.adapterPosition)

        Snackbar.make(holder.itemView, "Deletado", Snackbar.LENGTH_LONG)
            .setAction("DESFAZER"){
                arrayOfMedias.add(removedPosition, removedItem)
                notifyItemInserted(removedPosition)
                RoomTMDBApplication.favoriteMediaDao.insert(MediaMapper.mediaToFavorite(removedItem))
            }.show()
    }
}

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(media: Media) {

        val imageMovie = itemView.findViewById<ImageView>(R.id.imvSearchFilm)

        val loadingProgressBar = itemView.findViewById<ProgressBar>(R.id.loadingAnimation)
        loadingProgressBar?.visibility = View.VISIBLE

        Glide.with(itemView.context)
            .load("http://image.tmdb.org/t/p/original/" + media.posterWide)
            .listener(
                LoadingIconAnimation(
                    loadingProgressBar
                )
            )
            .into(imageMovie)

        val titleSearch = itemView.findViewById<TextView>(R.id.tvMovieSearchTitle)
        titleSearch.text = media.title

        val releaseDate = itemView.findViewById<TextView>(R.id.tvMovieSearchDate)
        media.releaseDate?.let {
            releaseDate.text = it.substring(0, 4)
        }

        itemView.setOnClickListener {
            val intent = Intent(itemView.context, MovieDetails::class.java)

            //Animation
            val activityOptionsCompat =
                ActivityOptionsCompat
                    .makeSceneTransitionAnimation(itemView.context as Activity,
                        imageMovie, "posterBack")

            //Dados
            val dados = Bundle()
            media.instant = ""
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