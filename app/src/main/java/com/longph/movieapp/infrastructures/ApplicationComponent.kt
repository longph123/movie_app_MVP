package com.longph.movieapp.infrastructures

import com.longph.movieapp.filters.ErrorFilter
import com.longph.movieapp.networks.NetworkProvider
import com.longph.movieapp.services.MovieService
import dagger.Component
import org.greenrobot.eventbus.EventBus
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {
    fun networkProvider(): NetworkProvider
    fun errorFilter(): ErrorFilter
    fun eventBus(): EventBus
    fun movieService(): MovieService
}