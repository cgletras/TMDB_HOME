package com.example.tmdb.activity

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.tmdb.R
import kotlinx.android.synthetic.main.activity_movie_details.*

class MovieDetails : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        val dados: Bundle? = intent.extras

        tvMovieTitleDetails.text = dados?.getString("title")
        tvMovieDetails.text = dados?.getString("details")

        val imageSerie = findViewById<ImageView>(R.id.imvMovieDetails)

        Glide.with(this)
            .load("http://image.tmdb.org/t/p/original/" + dados?.getString("poster"))
            .into(imageSerie)

        val imageSerieBack = findViewById<ImageView>(R.id.imvMovieDetailsBackPoster)
        Glide.with(this)
            .load("http://image.tmdb.org/t/p/original/" + dados?.getString("posterWide"))
            .into(imageSerieBack)
    }
}
