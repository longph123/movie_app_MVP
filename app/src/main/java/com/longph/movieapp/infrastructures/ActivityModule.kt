package com.longph.movieapp.infrastructures

import android.app.Activity
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ActivityModule {
    var activity: Activity

    constructor(activity: Activity) {
        this.activity = activity
    }

    @Provides
    @Singleton
    fun provideActivity(): Activity {
        return activity
    }
}