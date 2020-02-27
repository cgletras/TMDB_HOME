package com.example.tmdb.adapter

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
import com.example.tmdb.ui.activity.MovieDetails
import com.example.tmdb.repository.data.Movie
import com.example.tmdb.ui.animation.LoadingIconAnimation
import com.google.android.material.snackbar.Snackbar

class SearchMovieAdapter : RecyclerView.Adapter<ViewHolder>() {

    private val arrayOfMovies = ArrayList<Movie>()

    private var removedPosition:Int = 0
    private lateinit var removedItem:Movie

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

    fun removeItem(holder: RecyclerView.ViewHolder) {
        removedPosition = holder.adapterPosition
        removedItem = arrayOfMovies[holder.adapterPosition]

        arrayOfMovies.removeAt(holder.adapterPosition)
        notifyItemRemoved(holder.adapterPosition)

        Snackbar.make(holder.itemView, "Deletado", Snackbar.LENGTH_LONG)
            .setAction("DESFAZER"){
                arrayOfMovies.add(removedPosition, removedItem)
                notifyItemInserted(removedPosition)
                RoomTMDBApplication.movieDao.insert(removedItem)
            }.show()

    }
}

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(movie: Movie) {

        val imageMovie = itemView.findViewById<ImageView>(R.id.imvSearchFilm)

        val loadingProgressBar = itemView.findViewById<ProgressBar>(R.id.loadingAnimation)
        loadingProgressBar?.visibility = View.VISIBLE

        Glide.with(itemView.context)
            .load("http://image.tmdb.org/t/p/original/" + movie.posterBack)
            .listener(LoadingIconAnimation(loadingProgressBar))
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
            movie.instant = ""
            dados.putString("instant", movie.instant)
            dados.putString("title", movie.title)
            dados.putString("poster", movie.poster)
            dados.putString("details", movie.details)
            dados.putString("posterWide",movie.posterBack)
            dados.putParcelable("moviesDetails", movie)

            intent.putExtras(dados)
            itemView.context.startActivity(intent, activityOptionsCompat.toBundle())
        }

    }
}