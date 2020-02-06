package com.example.tmdb.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.tmdb.R
import com.example.tmdb.adapter.MenuMovieAdapterViewPager
import com.example.tmdb.adapter.MenuSeriesAdapterRecyclerView
import com.example.tmdb.adapter.SearchMovieAdapterRecyclerViewByGenre
import com.example.tmdb.data.*
import com.example.tmdb.service.RetrofitInitializer
import kotlinx.android.synthetic.main.fragment_menu.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MenuFragment : Fragment() {

    companion object {

        fun newInstance() = MenuFragment()
    }

    lateinit var adapter: MenuMovieAdapterViewPager
    lateinit var adapterSerie: MenuSeriesAdapterRecyclerView
    lateinit var adapterMovieByGenre: SearchMovieAdapterRecyclerViewByGenre

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Define adapters
        adapterSerie = MenuSeriesAdapterRecyclerView()
        rvMenuSeries.adapter = adapterSerie

        adapterMovieByGenre = SearchMovieAdapterRecyclerViewByGenre()
        rvMenuSeriesGrid.adapter = adapterMovieByGenre


        //Define Spinner

        var genreList = hashMapOf<String, Int>()
        val spinner: Spinner = view.findViewById(R.id.spGeneros) as Spinner
        //LoadArrayFromAPI
        loadSpinnerContent(genreList, spinner)

        //SCROLL UP GENRE

        var displayFirstTime = true

        spinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (displayFirstTime) {
                    displayFirstTime = false
                } else {

                    val genreTitle = spinner.selectedItem.toString()

                    val changeScrollPosition: IntArray = intArrayOf(0, 0)
                    divider2.getLocationInWindow(changeScrollPosition)
                    nestedScrollMenu.smoothScrollTo(0, divider2.y.toInt())

                    loadGenreImage(genreTitle)
                    loadMoviesByGenre(genreTitle)
                }
            }
        }
        //--------------------------------------------
        // Load populate services
        //--------------------------------------------
        loadPopMovies()
        loadSeries()
        tvSeriesTitle.text = "Séries no ar hoje!"

    }

    private fun loadSpinnerContent(
        genreList: HashMap<String, Int>,
        spinner: Spinner
    ) {
        RetrofitInitializer().apiService()
            .listGenres("385801b00919de93e960028b6ca5e4cd", "en-US")
            .enqueue(object : Callback<GenreList> {
                override fun onResponse(call: Call<GenreList>, response: Response<GenreList>) {
                    response.body()?.let {
                       it.genres.forEach {genre ->
                           genreList[genre.name] = genre.id
                       }
                        val arrayadapter = context?.let { it1 -> ArrayAdapter(it1, android.R.layout.simple_spinner_item, genreList.keys.toList()) }
                       spinner.adapter = arrayadapter
                    }
                }

                override fun onFailure(call: Call<GenreList>, t: Throwable) {
                    Log.e("TMDB", t.stackTrace.toString())
                }
            })
    }

    private fun loadGenreImage(
        genreTitle: String
    ) {
        RetrofitInitializer().apiServicePixaBay()
            .getGenreImage(
                "15090335-0cc1865286155508154e4f14f",
                genreTitle,
                "photo",
                "true",
                "horizontal"
            )
            .enqueue(object : Callback<GenreListImage> {
                override fun onFailure(call: Call<GenreListImage>, t: Throwable) {
                    Log.e("TMDB", t.stackTrace.toString())
                }

                override fun onResponse(call: Call<GenreListImage>, response: Response<GenreListImage>) {
                    response
                    response.body()?.let { hits ->

                        if (hits.genresPixabay.isEmpty()) {

                        } else {

                            val listSize = hits.genresPixabay.size

                            //Glide

                            Glide.with(requireContext())
                                .load(hits.genresPixabay[(0..listSize).random()].genreImage)
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .into(ivGenreImage)
                        }
                    }
                }
            })
    }

    private fun loadSeries() {
        RetrofitInitializer().apiService()
            .listSeriesToday("385801b00919de93e960028b6ca5e4cd", "en-US", 1)
            .enqueue(object : Callback<SerieList> {
                override fun onFailure(call: Call<SerieList>, t: Throwable) {
                    Log.e("TMDB", t.stackTrace.toString())
                }

                override fun onResponse(call: Call<SerieList>, response: Response<SerieList>) {
                    response.body()?.let { result ->
                        adapterSerie.setList(result.series)
                    }
                }
            })
    }

    private fun loadPopMovies() {
        RetrofitInitializer().apiService()
            .listPopularMovies("385801b00919de93e960028b6ca5e4cd", "en-US", 1)
            .enqueue(object : Callback<MovieList> {
                override fun onFailure(call: Call<MovieList>, t: Throwable) {
                    Log.e("TMDB", t.stackTrace.toString())
                }

                override fun onResponse(
                    call: Call<MovieList>,
                    response: Response<MovieList>
                ) {
                    response.body()?.let {
                        adapter = MenuMovieAdapterViewPager(it.movies)
                        vpMenu?.adapter = adapter
                    }
                }
            })
    }

    private fun loadMoviesByGenre(genreTitle: String) {
        RetrofitInitializer().apiService()
            .listMoviesByGenre("385801b00919de93e960028b6ca5e4cd", "en-US", "popularity.desc","false","false",28, 1)
            .enqueue(object :Callback<MovieList>{
                override fun onFailure(call: Call<MovieList>, t: Throwable) {
                    Log.e("TMDB", t.stackTrace.toString())
                }

                override fun onResponse(call: Call<MovieList>, response: Response<MovieList>) {
                   response.body()?.let {
                       adapterMovieByGenre.setList(it.movies)
                   }
                }

            })
    }
}
