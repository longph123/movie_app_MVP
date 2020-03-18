package com.longph.movieapp.infrastructures.scopes

import javax.inject.Qualifier
import javax.inject.Scope

@Qualifier
@Retention(value = AnnotationRetention.RUNTIME)
@Scope
annotation class ActivityScope {
}