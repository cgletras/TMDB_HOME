package com.example.tmdb.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.example.tmdb.R
import com.example.tmdb.data.Serie

class MenuSeriesAdapterViewPager(private val arrayOfseries: List<Serie>) : PagerAdapter() {

    override fun isViewFromObject(view: View, `object`: Any) =
        view === `object`

    override fun getCount() = arrayOfseries.size

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(container.context)
            .inflate(R.layout.slider_image_serie_item, container, false)

        val vpSerieImage = view.findViewById<ImageView>(R.id.imSliderImageSerie)

        //Glide
        Glide.with(container)
            .load("http://image.tmdb.org/t/p/original/" + arrayOfseries[position].poster)
            .into(vpSerieImage)

        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}