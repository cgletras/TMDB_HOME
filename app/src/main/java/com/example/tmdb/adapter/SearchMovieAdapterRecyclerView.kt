package com.example.tmdb.adapter

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tmdb.R
import com.example.tmdb.activity.MovieDetails
import com.example.tmdb.data.Movie

class SearchMovieAdapter : RecyclerView.Adapter<ViewHolder>() {

    private val arrayOfMovies = ArrayList<Movie>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_search_recycler, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount() = arrayOfMovies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(arrayOfMovies[position])
    }

    fun setList(movies: List<Movie>) {
        arrayOfMovies.clear()
        arrayOfMovies.addAll(movies)
        notifyDataSetChanged()
    }
}

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(movie: Movie) {

        val imageMovie = itemView.findViewById<ImageView>(R.id.imvSearchFilm)
        Glide.with(itemView.context)
            .load("http://image.tmdb.org/t/p/original/" + movie.posterBack)
            .thumbnail()
            .into(imageMovie)

        val titleSearch = itemView.findViewById<TextView>(R.id.tvMovieSearchTitle)
        titleSearch.text = movie.title

        val releaseDate = itemView.findViewById<TextView>(R.id.tvMovieSearchDate)
        movie.releaseDate?.let {
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
            dados.putString("title", movie.title)
            dados.putString("poster", movie.poster)
            dados.putString("details", movie.details)
            dados.putString("posterWide",movie.posterBack)

            intent.putExtras(dados)
            itemView.context.startActivity(intent, activityOptionsCompat.toBundle())
        }

    }
}