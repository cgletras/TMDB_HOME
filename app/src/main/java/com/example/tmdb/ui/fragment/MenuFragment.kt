package com.example.tmdb.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.AdapterView.VISIBLE
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.tmdb.R
import com.example.tmdb.adapter.MenuMovieAdapterViewPager
import com.example.tmdb.adapter.MenuSeriesAdapterRecyclerView
import com.example.tmdb.adapter.SearchMovieAdapterRecyclerViewByGenre
import com.example.tmdb.repository.data.GenreList
import com.example.tmdb.repository.data.GenreListImage
import com.example.tmdb.repository.data.MovieList
import com.example.tmdb.repository.data.SerieList
import com.example.tmdb.repository.remote.RetrofitInitializer
import com.example.tmdb.ui.animation.LoadingIconAnimation
import kotlinx.android.synthetic.main.fragment_menu.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MenuFragment : Fragment() {

    companion object {
        fun newInstance() = MenuFragment()
    }
    private lateinit var adapter: MenuMovieAdapterViewPager
    private lateinit var adapterSerie: MenuSeriesAdapterRecyclerView
    private lateinit var adapterMovieByGenre: SearchMovieAdapterRecyclerViewByGenre
    private lateinit var genreList: HashMap<String, Int>
    private val retrofitPixaBay = RetrofitInitializer().apiServicePixaBay()
    private val retrofitTMDB = RetrofitInitializer().apiService()
    private lateinit var spinner: Spinner

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
        genreList = hashMapOf<String, Int>()
        spinner = view.findViewById(R.id.spGeneros) as Spinner

        //LoadArrayFromAPI
        loadSpinnerContent(genreList, spinner)

        //SCROLL UP GENRE
        configSpinnerListener()

        //--------------------------------------------
        // Load populate services
        //--------------------------------------------

        loadPopMovies()
        loadSeries()
    }

    private fun configSpinnerListener() {
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
                    loadGenreImage(genreTitle)
                    loadMoviesByGenre(genreList[genreTitle])

                    val changeScrollPosition: IntArray = intArrayOf(0, 0)
                    divider2.getLocationInWindow(changeScrollPosition)
                    nestedScrollMenu.smoothScrollTo(0, divider2.y.toInt())
                }
            }
        }
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
                        genreList.put("", 0)
                        it.genres.forEach { genre ->
                            genreList[genre.name] = genre.id
                        }
                        val arrayadapter = context?.let { it1 ->
                            ArrayAdapter(
                                it1,
                                android.R.layout.simple_spinner_item,
                                genreList.keys.toList()
                            )
                        }
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

        retrofitPixaBay.getGenreImage(
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

                override fun onResponse(
                    call: Call<GenreListImage>,
                    response: Response<GenreListImage>
                ) {
                    response.body()?.let { hits ->
                        if (hits.genresPixabay.isEmpty()) {
                        } else {
                            val listSize = hits.genresPixabay.size

                            val loadingProgressBar = view?.findViewById<ProgressBar>(R.id.loadingAnimationGenre)
                            loadingProgressBar?.visibility = View.VISIBLE

                            loadingProgressBar?.let {
                                Glide.with(requireContext())
                                    .load(hits.genresPixabay[(0 until listSize - 1).random()].genreImage)
                                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                                    .listener(LoadingIconAnimation(it))
                                    .into(ivGenreImage)
                            }
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

    private fun loadMoviesByGenre(genreTitleInt: Int?) {
        genreTitleInt?.let {

            retrofitTMDB.listMoviesByGenre(
                "385801b00919de93e960028b6ca5e4cd", "en-US", "popularity.desc", "false", "false",
                it, 1
            )
                .enqueue(object : Callback<MovieList> {
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
}
