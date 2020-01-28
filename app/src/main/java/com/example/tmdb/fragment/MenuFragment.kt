package com.example.tmdb.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tmdb.R
import com.example.tmdb.adapter.MenuAdapter
import com.example.tmdb.adapter.MenuSeriesAdapter
import com.example.tmdb.data.PopMovieList
import com.example.tmdb.data.SerieList
import com.example.tmdb.service.RetrofitInitializer
import kotlinx.android.synthetic.main.fragment_menu.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MenuFragment : Fragment() {

    companion object {
        fun newInstance() = MenuFragment()
    }

    lateinit var adapter: MenuAdapter
    lateinit var adapterSerie: MenuSeriesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       loadPopMovies()
        loadSeries()
        tvSeriesTitle.text = "Séries"

    }

    private fun loadSeries() {
        //carreguei filmes pois o adapter das series esta dando pau

        RetrofitInitializer().apiService().listSeriesToday("385801b00919de93e960028b6ca5e4cd", "en-US", 1)
            .enqueue(object : Callback<SerieList>{
                override fun onFailure(call: Call<SerieList>, t: Throwable) {
                    Log.e("TMDB", t.stackTrace.toString())
                }

                override fun onResponse(
                    call: Call<SerieList>,
                    response: Response<SerieList>
                ) {
                    response.body()?.let {
                       adapterSerie = MenuSeriesAdapter(it.series)
                       //vpMenuSeries.adapter = adapter
                    }
                }
            })
    }
    private fun loadPopMovies() {
        RetrofitInitializer().apiService().listPopularMovies("385801b00919de93e960028b6ca5e4cd", "en-US", 1)
            .enqueue(object : Callback<PopMovieList>{
                override fun onFailure(call: Call<PopMovieList>, t: Throwable) {
                    Log.e("TMDB", t.stackTrace.toString())
                }

                override fun onResponse(
                    call: Call<PopMovieList>,
                    response: Response<PopMovieList>
                ) {
                    response.body()?.let {
                        adapter = MenuAdapter(it.movies)
                        vpMenu.adapter = adapter
                    }
                }
            })
    }


}
