package com.example.tmdb.adapter

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tmdb.R
import com.example.tmdb.activity.MovieDetails
import com.example.tmdb.data.Movie

class SearchMovieAdapterRecyclerViewByGenre: RecyclerView.Adapter<MovieByGenreViewholder>(){

    private val arrayOfMoviesByGenre = ArrayList<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieByGenreViewholder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cd_serie_item_recycler, parent, false)

        return MovieByGenreViewholder(view)
    }

    override fun getItemCount() = arrayOfMoviesByGenre.size

    override fun onBindViewHolder(holder: MovieByGenreViewholder, position: Int) {
        holder.bind(arrayOfMoviesByGenre[position])
    }

    fun setList(movie: List<Movie>) {
        arrayOfMoviesByGenre.clear()
        arrayOfMoviesByGenre.addAll(movie)
        notifyDataSetChanged()
    }

}

class MovieByGenreViewholder(itemView: View):RecyclerView.ViewHolder(itemView) {

    fun bind (movie:Movie){
        val imageMovie = itemView.findViewById<ImageView>(R.id.imvSeriePosterPath)
        Glide.with(itemView.context)
            .load("http://image.tmdb.org/t/p/original/" + movie.poster)
            .into(imageMovie)

        itemView.setOnClickListener {
            val intent = Intent(itemView.context , MovieDetails::class.java)

            //Animation
            val activityOptionsCompat =
                ActivityOptionsCompat
                    .makeSceneTransitionAnimation(itemView.context as Activity,
                        imageMovie, "posterSerie")

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
