package com.longph.movieapp.infrastructures

import android.app.Application
import com.longph.movieapp.filters.ErrorFilter
import com.longph.movieapp.filters.NetworkFilter
import com.longph.movieapp.networks.DefaultNetworkProvider
import com.longph.movieapp.networks.NetworkProvider
import com.longph.movieapp.services.DefaultMovieService
import com.longph.movieapp.services.MovieService
import com.longph.movieapp.services.RestMovieServiceApi
import com.longph.movieapp.utils.ApiInfos
import dagger.Module
import dagger.Provides
import org.greenrobot.eventbus.EventBus
import javax.inject.Singleton

@Module
class ApplicationModule {
    var application: Application

    constructor(application: Application) {
        this.application = application
    }

    @Provides
    @Singleton
    fun provideApplication(): Application {
        return application
    }

    @Provides
    @Singleton
    fun provideErrorFilter(networkProvider: NetworkProvider): ErrorFilter {
        return ErrorFilter(networkProvider)
    }

    @Provides
    @Singleton
    fun provideNetworkProvider(): NetworkProvider {
        return DefaultNetworkProvider(application)
    }

    @Provides
    @Singleton
    fun provideEventBus(): EventBus {
        return EventBus.getDefault()
    }

    @Provides
    @Singleton
    fun provideMovieService(networkProvider: NetworkProvider, errorFilter: ErrorFilter): MovieService {
        var restMovieServiceApi: RestMovieServiceApi = provideNetworkProvider()
                .provideApi(ApiInfos.API_SERVER_ROOT_URL, RestMovieServiceApi::class.java)

        return DefaultMovieService(networkProvider, restMovieServiceApi, errorFilter)
    }
}