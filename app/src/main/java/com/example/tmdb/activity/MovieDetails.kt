package com.example.tmdb.activity

import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.tmdb.R
import com.example.tmdb.RoomTMDBApplication
import com.example.tmdb.adapter.SearchMovieAdapter
import com.example.tmdb.data.Movie
import kotlinx.android.synthetic.main.activity_movie_details.*

class MovieDetails : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        val dados: Bundle? = intent.extras

        val movieDetails = dados?.getParcelable<Movie>("moviesDetails")

        var favorite = false

        tvMovieTitleDetails.text = dados?.getString("title")
        tvMovieDetails.text = dados?.getString("details")
        val iconView = findViewById<ImageView>(R.id.imvIconFavorite)
        val imageSerie = findViewById<ImageView>(R.id.imvMovieDetails)

        Glide.with(this)
            .load("http://image.tmdb.org/t/p/original/" + dados?.getString("poster"))
            .into(imageSerie)

        val imageSerieBack = findViewById<ImageView>(R.id.imvMovieDetailsBackPoster)
        Glide.with(this)
            .load("http://image.tmdb.org/t/p/original/" + dados?.getString("posterWide"))
            .into(imageSerieBack)

        //checkIfFavorite
        var FavoriteMovies = RoomTMDBApplication.movieDao.getAllMovies() as ArrayList

        for(favoriteMovie in FavoriteMovies)
            if(favoriteMovie.id == movieDetails?.id){
                iconView.setImageResource(R.drawable.ic_favorite_green_24dp)
                favorite = true
            }

        imvIconFavorite.setOnClickListener {

            if (favorite == false) {
                iconView.setImageResource(R.drawable.ic_favorite_green_24dp)
                favorite = true

                val instantLong = System.currentTimeMillis() / 1000
                movieDetails?.instant = instantLong.toString()

                movieDetails?.let { it1 -> RoomTMDBApplication.movieDao.insert(it1) }
                Toast.makeText(applicationContext,"Filme adicionado aos favoritos",Toast.LENGTH_SHORT).show()

            } else {
                iconView.setImageResource(R.drawable.ic_favorite_border_green_24dp)
                favorite = false

                movieDetails?.let { it1 -> RoomTMDBApplication.movieDao.deleteMovie(it1) }
                Toast.makeText(applicationContext,"Filme removido dos favoritos",Toast.LENGTH_SHORT).show()
            }

        }

    }
}
