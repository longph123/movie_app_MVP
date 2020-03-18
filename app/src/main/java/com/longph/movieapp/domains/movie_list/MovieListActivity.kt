package com.longph.movieapp.domains.movie_list

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.longph.movieapp.R
import com.longph.movieapp.MainApplication
import com.longph.movieapp.ObjectBox
import com.longph.movieapp.domains.base.BaseActivity
import com.longph.movieapp.domains.movie_detail.MovieDetailActivity
import com.longph.movieapp.domains.movie_list.adapters.MovieListAdapter
import com.longph.movieapp.domains.movie_list.listeners.PaginationScrollListener
import com.longph.movieapp.infrastructures.ActivityModule
import com.longph.movieapp.infrastructures.DaggerMainComponent
import com.longph.movieapp.models.Movie
import com.longph.movieapp.models.Movie_
import com.longph.movieapp.models.enums.MovieListType
import io.objectbox.Box
import io.objectbox.android.AndroidScheduler
import io.objectbox.query.Query
import io.reactivex.android.schedulers.AndroidSchedulers
import org.androidannotations.annotations.*
import javax.inject.Inject

class MovieListActivity : BaseActivity<MovieListView, MovieListPresenter>(), MovieListView {

    @Inject
    lateinit var moviePresenter: MovieListPresenter

    private var movieListType: MovieListType = MovieListType.POPULAR
    private var rcvMovieList: RecyclerView? = null
    private var movieListAdapter: MovieListAdapter? = null

    private var isLastPage: Boolean = false
    private var isLoading: Boolean = false
    private var page: Int = 1

    private lateinit var movieBox: Box<Movie>
    private lateinit var movieQuery: Query<Movie>

    private var movieList: ArrayList<Movie> = ArrayList<Movie>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)

        movieBox = ObjectBox.boxStore.boxFor(Movie::class.java)

        setSupportActionBar(findViewById(R.id.toolbar) as Toolbar)

        rcvMovieList = findViewById<RecyclerView>(R.id.activity_movie_list_rcv_movie_list)

        var autoFitGridLayoutManager: AutoFitGridLayoutManager = AutoFitGridLayoutManager(this, 500)
        rcvMovieList?.layoutManager = autoFitGridLayoutManager
        rcvMovieList?.addOnScrollListener(object : PaginationScrollListener(autoFitGridLayoutManager) {
            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

            override fun loadMoreItems() {
                isLoading = true
                page++
                moviePresenter.getMovieList(movieListType, page)
            }
        })

        movieListAdapter = MovieListAdapter(this, movieList) { movie ->
            val intent = MovieDetailActivity.newIntent(this, movie.id)
            startActivity(intent)
        }
        rcvMovieList?.adapter = movieListAdapter

        moviePresenter.attachView(this)

        moviePresenter.getMovieList(movieListType, page)

    }

    override fun getMovieListSuccess(movieList: ArrayList<Movie>) {
        isLoading = false
        if (movieList == null || (movieList != null && movieList.size == 0)) {
            isLastPage = true
        }

        for (movie in movieList) {
            movie.movieType = movieListType.ordinal
        }

        if (page == 1) {
            movieListAdapter!!.resetData(movieList)
        } else {
            movieListAdapter!!.addData(movieList)
        }

        movieBox.put(movieList)
    }

    override fun getApiError(throwable: Throwable) {
        handleErrorMessage(throwable)
        loadOfflineMovieList()
    }

    override fun showLoading() {
        showHUD()
    }

    override fun hideLoading() {
        hideHUD()
    }

    override fun createPresenter(): MovieListPresenter {
        DaggerMainComponent.builder()
                .applicationComponent((application as MainApplication).applicationComponent)
                .activityModule(ActivityModule(this))
                .build()
                .inject(this)
        return moviePresenter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.movie_types, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.getItemId()) {
            R.id.mn_movie_type_popular -> {
                if (movieListType != MovieListType.POPULAR) {
                    movieListType = MovieListType.POPULAR
                    page = 1
                    moviePresenter.getMovieList(movieListType, page)
                }
            }
            R.id.mn_movie_type_top_rated -> {
                if (movieListType != MovieListType.TOP_RATED) {
                    movieListType = MovieListType.TOP_RATED
                    page = 1
                    moviePresenter.getMovieList(movieListType, page)
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }

    fun loadOfflineMovieList() {
        if (movieBox.count() > 0) {
            movieQuery = movieBox.query().equal(Movie_.movieType, movieListType.ordinal.toLong()).build()
            movieQuery.subscribe().on(AndroidScheduler.mainThread())
                    .observer { movies -> movieListAdapter!!.resetData(ArrayList(movies)) }
        }
    }
}