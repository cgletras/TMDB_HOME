package com.example.tmdb

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val btGenres by lazy { findViewById<Button>(R.id.btGenres) }
    private lateinit var tvGenre: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvGenre = findViewById(R.id.tvGenre)

        btGenres.setOnClickListener {
            RetrofitInitializer().apiService().listGenres("385801b00919de93e960028b6ca5e4cd", "en-US")
                .enqueue(object : Callback<GenreList> {
                override fun onResponse(call: Call<GenreList>, response: Response<GenreList>) {
                    response.body()?.let {
                        for (genre in it.genres) {
                            tvGenre.append("Nome: ${genre.name}\n")
                        }
                    }
                }

                override fun onFailure(call: Call<GenreList>, t: Throwable) {
                    Log.e("TMDB", t.stackTrace.toString())
                }
            })
        }
    }
}


