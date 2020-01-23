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

    private val btUsers by lazy { findViewById<Button>(R.id.btUsers) }
    private lateinit var tvUsers: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvUsers = findViewById(R.id.tvUsers)

        btUsers.setOnClickListener {
            RetrofitInitializer().apiService().list().enqueue(object : Callback<List<User>> {
                override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                    response.body()?.let {
                        for (user in it) {
                            tvUsers.append("Nome: ${user.nome}\n")
                        }
                    }
                }

                override fun onFailure(call: Call<List<User>?>, t: Throwable) {
                    Log.e("TMDB", t.stackTrace.toString())
                }
            })
        }
    }
}


