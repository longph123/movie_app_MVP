package com.longph.movieapp.domains.movie_detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.longph.movieapp.MainApplication
import com.longph.movieapp.ObjectBox
import com.longph.movieapp.R
import com.longph.movieapp.domains.base.BaseActivity
import com.longph.movieapp.domains.movie_list.AutoFitGridLayoutManager
import com.longph.movieapp.domains.movie_list.MovieListPresenter
import com.longph.movieapp.infrastructures.ActivityModule
import com.longph.movieapp.infrastructures.DaggerMainComponent
import com.longph.movieapp.models.Movie
import com.longph.movieapp.models.MovieDetail
import com.longph.movieapp.models.MovieDetail_
import com.longph.movieapp.utils.ApiInfos
import io.objectbox.Box
import io.objectbox.android.AndroidScheduler
import io.objectbox.query.Query
import javax.inject.Inject

class MovieDetailActivity : BaseActivity<MovieDetailView, MovieDetailPresenter>(), MovieDetailView {

    @Inject
    lateinit var moviePresenter: MovieDetailPresenter

    private var tvRating: TextView? = null
    private var tvTitle: TextView? = null
    private var tvOverview: TextView? = null

    private var movieId: Long = 0

    private lateinit var movieBox: Box<MovieDetail>
    private lateinit var movieQuery: Query<MovieDetail>

    companion object {

        private val INTENT_USER_ID = "user_id"

        fun newIntent(context: Context, movieId: Long): Intent {
            val intent = Intent(context, MovieDetailActivity::class.java)
            intent.putExtra(INTENT_USER_ID, movieId)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        movieBox = ObjectBox.boxStore.boxFor(MovieDetail::class.java)

        movieId = intent.getLongExtra(INTENT_USER_ID, 0)

        tvRating = findViewById(R.id.activity_movie_detail_tv_rating) as TextView
        tvTitle = findViewById(R.id.activity_movie_detail_tv_title) as TextView
        tvOverview = findViewById(R.id.activity_movie_detail_tv_overview) as TextView

        moviePresenter.attachView(this)

        moviePresenter.getMovieDetail(movieId)

    }

    override fun getMovieDetailSuccess(movieDetail: MovieDetail) {
        showMovieInfo(movieDetail)

        movieBox.put(movieDetail)
    }

    override fun getApiError(throwable: Throwable) {
        handleErrorMessage(throwable)
        loadOfflineMovieInfo()
    }

    override fun hideLoading() {
        hideHUD()
    }

    override fun showLoading() {
        showHUD()
    }

    override fun createPresenter(): MovieDetailPresenter {
        DaggerMainComponent.builder()
                .applicationComponent((application as MainApplication).applicationComponent)
                .activityModule(ActivityModule(this))
                .build()
                .inject(this)
        return moviePresenter
    }

    fun showMovieInfo(movieDetail: MovieDetail?) {
        if (movieDetail != null) {
            Glide.with(this)
                    .load(ApiInfos.IMAGE_BASE_URL + movieDetail.poster_path)
                    .into(findViewById(R.id.activity_movie_detail_iv_poster) as ImageView)
            tvTitle?.text = movieDetail.original_title
            tvRating?.text = movieDetail.vote_average.toString()
            tvOverview?.text = movieDetail.overview
        }
    }

    fun backToMainScreen(view: View) {
        onBackPressed()
    }

    fun loadOfflineMovieInfo() {
        if (movieBox.count() > 0) {
            movieQuery = movieBox.query().equal(MovieDetail_.id, movieId).build()
            showMovieInfo(movieQuery.findFirst())
        }
    }
}