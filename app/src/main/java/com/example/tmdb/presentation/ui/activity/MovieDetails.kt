package com.example.tmdb.presentation.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.tmdb.R
import com.example.tmdb.RoomTMDBApplication
import com.example.tmdb.data.repository.remote.entity.Serie
import com.example.tmdb.domain.Media
import com.example.tmdb.domain.MediaMapper
import com.example.tmdb.presentation.ui.animation.LoadingIconAnimation
import kotlinx.android.synthetic.main.activity_movie_details.*

class MovieDetails : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        val dados: Bundle? = intent.extras

        val mediaDetails = dados?.getParcelable<Media>("mediaDetails")
//        val serieDetails = dados?.getParcelable<Serie>("seriesDetails")

        var favorite = false

        tvMovieTitleDetails.text = dados?.getString("title")
        tvMovieDetails.text = dados?.getString("details")
        val iconView = findViewById<ImageView>(R.id.imvIconFavorite)
        val imageSerie = findViewById<ImageView>(R.id.imvMovieDetails)

        //Glide + Progress bar
        val loadingProgressBar = findViewById<ProgressBar>(R.id.loadingAnimation)
        loadingProgressBar?.visibility = View.VISIBLE

        Glide.with(this)
            .load("http://image.tmdb.org/t/p/original/" + dados?.getString("poster"))
            .listener(
                LoadingIconAnimation(
                    loadingProgressBar
                )
            )
            .into(imageSerie)

        val imageSerieBack = findViewById<ImageView>(R.id.imvMovieDetailsBackPoster)
        Glide.with(this)
            .load("http://image.tmdb.org/t/p/original/" + dados?.getString("posterWide"))
            .into(imageSerieBack)

        //checkIfFavorite
        var FavoriteMedia = RoomTMDBApplication.favoriteMediaDao.getAllFavorites() as ArrayList

        for (favoriteMedia in FavoriteMedia)
            if (favoriteMedia.id == mediaDetails?.id) {
                iconView.setImageResource(R.drawable.ic_favorite_green_24dp)
                favorite = true
            }

        imvIconFavorite.setOnClickListener {

            if (favorite == false) {
                iconView.setImageResource(R.drawable.ic_favorite_green_24dp)
                favorite = true

                val instantLong = System.currentTimeMillis() / 1000

                mediaDetails?.let { media ->
                    val favoriteMedia = MediaMapper.mediaToFavorite(media)
                    favoriteMedia.instant = instantLong.toString()
                    RoomTMDBApplication.favoriteMediaDao.insert(favoriteMedia)
                }

                Toast.makeText(
                    applicationContext,
                    "Adicionado aos favoritos",
                    Toast.LENGTH_SHORT
                ).show()

            } else {
                iconView.setImageResource(R.drawable.ic_favorite_border_green_24dp)
                favorite = false

                mediaDetails?.let { media ->
                    val favoriteMedia = MediaMapper.mediaToFavorite(media)
                    RoomTMDBApplication.favoriteMediaDao.deleteFavorite(favoriteMedia)
                }
                Toast.makeText(
                    applicationContext,
                    "Filme removido dos favoritos",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        imvIconShare.setOnClickListener {
            try {
                val sharingIntent = Intent(Intent.ACTION_SEND)
                sharingIntent.type = "text/plain"
                val message = dados?.getString("title")
                //     sharingIntent.setPackage("com.whatsapp")

                sharingIntent.putExtra(Intent.EXTRA_TEXT, message)
                startActivity(sharingIntent)

            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(applicationContext, "FODEU...", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
