package com.example.tmdb.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.example.tmdb.R
import com.example.tmdb.data.Serie

class MenuSeriesAdapter(private val arrayOfseries: List<Serie>) : PagerAdapter() {

    override fun isViewFromObject(view: View, `object`: Any) =
        view === `object`

    override fun getCount() = arrayOfseries.size

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(container.context)
            .inflate(R.layout.vp_image_serie_item, container, false)

        val tvSerie = view.findViewById<ImageView>(R.id.vpMenuSeries)

        //Glide
        Glide.with(container)
            .load("http://image.tmdb.org/t/p/w185/" + arrayOfseries[position].poster)
            .into(tvSerie)

        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}