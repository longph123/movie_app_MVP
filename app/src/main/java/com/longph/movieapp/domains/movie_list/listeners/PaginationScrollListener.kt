package com.longph.movieapp.domains.movie_list.listeners

import android.support.v7.widget.RecyclerView
import com.longph.movieapp.domains.movie_list.AutoFitGridLayoutManager

abstract class PaginationScrollListener(var gridLayoutManager: AutoFitGridLayoutManager) : RecyclerView.OnScrollListener() {
    abstract fun isLastPage(): Boolean

    abstract fun isLoading(): Boolean

    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val visibleItemCount = gridLayoutManager.childCount
        val totalItemCount = gridLayoutManager.itemCount
        val firstVisibleItemPosition = gridLayoutManager.findFirstVisibleItemPosition()

        if (!isLoading() && !isLastPage()) {
            if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
                loadMoreItems()
            }
        }
    }

    abstract fun loadMoreItems()
}