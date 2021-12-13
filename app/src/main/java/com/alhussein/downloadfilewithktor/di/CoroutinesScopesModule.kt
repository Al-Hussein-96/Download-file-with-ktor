package com.alhussein.downloadfilewithktor.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object CoroutinesScopesModule {

    @Singleton
    @Provides
    fun providesCoroutineScopeIO(
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): CoroutineScope = CoroutineScope(ioDispatcher)

//    @Singleton
//    @Provides
//    fun providesCoroutineScopeMain(
//        @MainDispatcher mainDispatcher: CoroutineDispatcher
//    ): CoroutineScope = CoroutineScope(mainDispatcher)
}