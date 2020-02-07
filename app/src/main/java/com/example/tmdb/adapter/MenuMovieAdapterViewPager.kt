package com.example.tmdb.adapter

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.example.tmdb.R
import com.example.tmdb.activity.MovieDetails
import com.example.tmdb.data.Movie

class MenuMovieAdapterViewPager(private val arrayOfMovies: List<Movie>) : PagerAdapter() {

    override fun isViewFromObject(view: View, `object`: Any) =
        view === `object`

    override fun getCount() = arrayOfMovies.size

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(container.context)
            .inflate(R.layout.slider_image_movie_item, container, false)

        val tvImage = view.findViewById<ImageView>(R.id.sliderImage)
        val tvTile = view.findViewById<TextView>(R.id.tvMovieTitle)

        //Glide
        Glide.with(container)
            .load("http://image.tmdb.org/t/p/original/" + arrayOfMovies[position].posterBack)
            .thumbnail()
            .into(tvImage)

       tvTile.text = arrayOfMovies[position].title

        view.setOnClickListener {
           val intent = Intent(container.context, MovieDetails::class.java)

            //Animation

            val activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(container.context as Activity, tvImage, "posterBack")

            val dados = Bundle()
            dados.putString("title", arrayOfMovies[position].title)
            dados.putString("poster", arrayOfMovies[position].poster)
            dados.putString("details", arrayOfMovies[position].details)
            dados.putString("posterWide",arrayOfMovies[position].posterBack)

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