package com.example.tmdb.presentation.ui.fragment

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdb.R
import com.example.tmdb.RoomTMDBApplication
import com.example.tmdb.data.repository.remote.entity.Movie
import com.example.tmdb.domain.Media
import com.example.tmdb.domain.MediaList
import com.example.tmdb.domain.MediaMapper
import com.example.tmdb.presentation.ui.adapter.SearchMovieAdapter
import kotlinx.android.synthetic.main.fragment_favorite.*

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class FavoriteFragment : Fragment() {

    private val adapter =
        SearchMovieAdapter()

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

        //Swipe to delete - Item A and B
        //Item A
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, position: Int) {
                //Remove from adapter list
                adapter.removeItem(viewHolder)

                //Remove from Room dataBase
                Log.i("POSITION ITEM LAYOUT", viewHolder.layoutPosition.toString())
                val moviesDel = RoomTMDBApplication.favoriteMediaDao.getAllFavorites() as ArrayList
                Log.i("POSITION ITEM LIST", moviesDel[viewHolder.layoutPosition].toString())
                RoomTMDBApplication.favoriteMediaDao.deleteFavorite(moviesDel[viewHolder.layoutPosition])
            }
        }
        //Item B
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(rvFavoriteRecycler)

        //------------------------
        //Remover toda a lista de favoritos

        btRemoveTudo.setOnClickListener {

            //Alert Dialog to confirm
            val builder = AlertDialog.Builder(view.context)
            builder.setTitle("Confirmar remoção")
            builder.setMessage("Deseja excluir toda a sua lista de favoritos? Tem volta não...")
            builder.setPositiveButton("SIM") { _, _ ->
                Toast.makeText(view.context, "Ok...lista apagada...perdeu heim", Toast.LENGTH_SHORT)
                    .show()

                RoomTMDBApplication.favoriteMediaDao.deleteAll()
                val movies = RoomTMDBApplication.favoriteMediaDao.getAllFavorites() as ArrayList
                val medias = ArrayList<Media>()
                movies.forEach {movie ->
                    medias.add(MediaMapper.favoriteToMedia(movie)) }

                adapter.setList(medias)
                //TODO Modificar a class do banco para MEDIA DATABASE
            }
            builder.setNegativeButton("NÃO PORRA") { _, _ ->
                Toast.makeText(view.context, "Ufa...não apaguei viu...", Toast.LENGTH_SHORT).show()
            }

            builder.setNeutralButton("CANCELA TUDO") { _, _ ->
                Toast.makeText(view.context, "Cancela a porra toda", Toast.LENGTH_SHORT).show()
            }

            val dialog: AlertDialog = builder.create()

            dialog.show()
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                .setTextColor(resources.getColor(R.color.colorPrimary))
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.GREEN)
            dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(Color.RED)
        }
    }

    private fun loadFavoriteDataBase() {

        var movies = RoomTMDBApplication.favoriteMediaDao.getAllFavorites() as ArrayList
        val medias = ArrayList<Media>()
        movies.forEach {movie ->
            medias.add(MediaMapper.favoriteToMedia(movie)) }

        adapter.setList(medias)
        //TODO Modificar a class do banco para MEDIA DATABASE
    }

    override fun onResume() {
        super.onResume()
        var movies = RoomTMDBApplication.favoriteMediaDao.getAllFavorites() as ArrayList

        val medias = ArrayList<Media>()
        movies.forEach {movie ->
            medias.add(MediaMapper.favoriteToMedia(movie)) }

        adapter.setList(medias)
        //TODO Modificar a class do banco para MEDIA DATABASE
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