package com.longph.movieapp.domains.base

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.view.Window
import com.hannesdorfmann.mosby3.mvp.MvpActivity
import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView
import com.longph.movieapp.R
import com.longph.movieapp.models.ApiThrowable
import org.androidannotations.annotations.EActivity
import org.androidannotations.annotations.UiThread
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

abstract class BaseActivity<V : MvpView, P : MvpPresenter<V>> : MvpActivity<V, P>() {

    private var progressDialogLoading: Dialog? = null

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    fun showHUD() {
        if (progressDialogLoading != null && progressDialogLoading!!.isShowing) {
        } else {
            val view = layoutInflater.inflate(R.layout.layout_progress_loading_ball_spin, null)
            progressDialogLoading = Dialog(this)
            progressDialogLoading!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            progressDialogLoading!!.setContentView(view)
            progressDialogLoading!!.setCancelable(false)
            progressDialogLoading!!.setCanceledOnTouchOutside(false)

            val window = progressDialogLoading!!.window
            window?.setBackgroundDrawableResource(R.drawable.bg_layout_loading)
            progressDialogLoading!!.show()
        }
    }

    fun hideHUD() {
        if (progressDialogLoading != null && progressDialogLoading!!.isShowing) {
            progressDialogLoading!!.dismiss()
        }
    }

    fun handleErrorMessage(throwable: Throwable) {
        var apiThrowable: ApiThrowable = throwable as ApiThrowable
        AlertDialog.Builder(this)
                .setTitle("Network Error")
                .setMessage(apiThrowable.errorMessage)
                .setCancelable(false)
                .setPositiveButton("OK", DialogInterface.OnClickListener { dialogInterface, i ->
                    {
                        dialogInterface.dismiss()
                    }
                })
                .create().show()
    }
}