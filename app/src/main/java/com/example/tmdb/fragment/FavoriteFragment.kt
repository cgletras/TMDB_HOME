package com.example.tmdb.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.tmdb.R
import com.example.tmdb.adapter.SearchMovieAdapter
import kotlinx.android.synthetic.main.fragment_favorite.*

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class FavoriteFragment : Fragment() {

    private val adapter = SearchMovieAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvFavoriteRecycler.adapter = adapter
        loadFavoriteDataBase()
    }

    private fun loadFavoriteDataBase() {
        btSalvar.setOnClickListener {
            var idMovie = testeLista.text.toString()
            Toast.makeText(context, "$idMovie", Toast.LENGTH_SHORT).show()

        }
    }

    companion object {
        fun newInstance(param1: String, param2: String) =
            FavoriteFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}