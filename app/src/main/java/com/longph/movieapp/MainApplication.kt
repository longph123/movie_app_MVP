package com.longph.movieapp

import android.support.multidex.MultiDexApplication
import com.longph.movieapp.infrastructures.ApplicationComponent
import com.longph.movieapp.infrastructures.ApplicationModule
import com.longph.movieapp.infrastructures.DaggerApplicationComponent
import uk.co.chrisjenx.calligraphy.CalligraphyConfig

class MainApplication : MultiDexApplication {

    var applicationComponent: ApplicationComponent? = null

    constructor() : super();

    override fun onCreate() {
        super.onCreate()

        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()

        CalligraphyConfig.initDefault(CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        )

        ObjectBox.build(this)
    }
}