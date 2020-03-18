package com.longph.movieapp.domains.movie_list.adapters

import android.content.Context
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.longph.movieapp.R
import com.longph.movieapp.models.Movie
import com.longph.movieapp.utils.ApiInfos
import kotlinx.android.synthetic.main.movie_list_item.view.*

class MovieListAdapter(val context: Context, val movieList: ArrayList<Movie>, val listener: (Movie) -> Unit) : RecyclerView.Adapter<MovieListAdapter.MovieItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieItemViewHolder {
        var itemView = LayoutInflater.from(context).inflate(R.layout.movie_list_item, parent, false)
        return MovieItemViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: MovieItemViewHolder, position: Int) {
        var item: Movie = movieList.get(position)
        Glide.with(context)
                .load(ApiInfos.IMAGE_BASE_URL + item.poster_path)
                .into(holder.ivPoster)
        holder.tvTitle.text = item.title
        holder.ivPoster.setOnClickListener {
            listener(item)
        }
    }

    fun resetData(listItems: ArrayList<Movie>) {
        var size = this.movieList.size
        this.movieList.clear()
        notifyItemRangeRemoved(0, size)
        this.movieList.addAll(listItems)
        notifyDataSetChanged()
    }

    fun addData(listItems: ArrayList<Movie>) {
        var size = this.movieList.size
        this.movieList.addAll(listItems)
        var sizeNew = this.movieList.size
        notifyItemRangeChanged(size, sizeNew)
    }

    class MovieItemViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {

        val ivPoster = itemview.movie_list_item_iv_poster
        val tvTitle = itemview.movie_list_item_tv_title
    }
}