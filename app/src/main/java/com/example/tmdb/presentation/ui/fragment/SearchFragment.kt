package com.example.tmdb.presentation.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.tmdb.R
import com.example.tmdb.presentation.ui.adapter.SearchMovieAdapter
import com.example.tmdb.data.repository.remote.entity.MovieList
import com.example.tmdb.data.repository.remote.RetrofitInitializer
import com.example.tmdb.domain.Media
import com.example.tmdb.domain.MediaMapper
import kotlinx.android.synthetic.main.fragment_search.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragment : Fragment() {

    private val adapter =
        SearchMovieAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Search IME_ACTION_SEARCH implementation
        etSearchTitle.setOnEditorActionListener { v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_SEARCH){
                searchResult()
                true
            } else {
                false
            }
        }
        rvSearchRecycler.adapter = adapter
        listSearchResults()
    }

    companion object {
        fun newInstance() =
            SearchFragment()
    }

    private fun listSearchResults() {

        imvLense.setOnClickListener {
            searchResult()
        }
    }

    private fun searchResult() {
        val searchBox = view?.findViewById<EditText>(R.id.etSearchTitle)
        val searchValue: String = searchBox?.text.toString().trim()
        if (searchValue == "") {
            Toast.makeText(context, "Digite algo na busca!", Toast.LENGTH_LONG).show()
        }

        RetrofitInitializer().apiService().listSearchResultMovies(
            "385801b00919de93e960028b6ca5e4cd", "en-US",
            searchValue, 1
        ).enqueue(object : Callback<MovieList> {
            override fun onFailure(call: Call<MovieList>, t: Throwable) {
                Log.e("TMDB", t.stackTrace.toString())
            }

            override fun onResponse(call: Call<MovieList>, response: Response<MovieList>) {
                response.body()?.let { result ->

                    val medias = ArrayList<Media>()
                    result.movies.forEach {movie ->
                        medias.add(MediaMapper.movieToMedia(movie)) }

                    adapter.setList(medias)
                }
            }
        })
    }


}
