package com.example.tmdb

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdb.adapter.SearchMovieAdapter

class SwipeHandler(val adapter: SearchMovieAdapter, dragDirs:Int, swipeDirs:Int) : ItemTouchHelper.SimpleCallback(dragDirs, swipeDirs){
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

       // adapter.removeRow(viewHolder.adapterPosition)
    }

}