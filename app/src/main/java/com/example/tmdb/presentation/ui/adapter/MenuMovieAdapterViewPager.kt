package com.example.tmdb.presentation.ui.adapter

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.example.tmdb.R
import com.example.tmdb.data.repository.remote.entity.Movie
import com.example.tmdb.domain.Media
import com.example.tmdb.presentation.ui.activity.MovieDetails
import com.example.tmdb.presentation.ui.animation.LoadingIconAnimation

class MenuMovieAdapterViewPager(private val arrayOfMovies: ArrayList<Media>) : PagerAdapter() {

    var clickTime = 0L

    override fun isViewFromObject(view: View, `object`: Any) =
        view === `object`

    override fun getCount() = arrayOfMovies.size

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(container.context)
            .inflate(R.layout.slider_image_movie_item, container, false)

        val tvImage = view.findViewById<ImageView>(R.id.sliderImage)
        val tvTile = view.findViewById<TextView>(R.id.tvMovieTitle)

        //Glide + Progress bar
        val loadingProgressBar = view.findViewById<ProgressBar>(R.id.loadingAnimation)
        loadingProgressBar.visibility = View.VISIBLE

        Glide.with(container)
            .load("http://image.tmdb.org/t/p/original/" + arrayOfMovies[position].posterWide)
            .listener(
                LoadingIconAnimation(
                    loadingProgressBar
                )
            )
            .into(tvImage)

       tvTile.text = arrayOfMovies[position].title

        view.setOnClickListener {

            //Multi click handler
            if (SystemClock.elapsedRealtime() - clickTime < 1000){
                return@setOnClickListener
            }
            clickTime = SystemClock.elapsedRealtime()
            val intent = Intent(container.context, MovieDetails::class.java)

            //Animation

            val activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(container.context as Activity, tvImage, "posterBack")

            val instantLong = System.currentTimeMillis() / 1000
            arrayOfMovies[position].instant = instantLong.toString()

            val dados = Bundle()
            dados.putString("title", arrayOfMovies[position].title)
            dados.putString("poster", arrayOfMovies[position].poster)
            dados.putString("details", arrayOfMovies[position].details)
            dados.putString("posterWide",arrayOfMovies[position].posterWide)
            dados.putString("id", arrayOfMovies[position].id.toString())
            dados.putParcelable("moviesDetails", arrayOfMovies[position])

            intent.putExtras(dados)

            container.context.startActivity(intent, activityOptionsCompat.toBundle())
        }

        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}


