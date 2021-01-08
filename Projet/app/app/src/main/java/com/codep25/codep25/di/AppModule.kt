package com.codep25.codep25.di

import android.content.Context
import com.codep25.codep25.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [
    ActivityBuilder::class,
    FragmentBuilder::class,
    ViewModelBuilder::class,
    RepositoryModule::class,
    NetworkModule::class
])
class AppModule {

    @Provides
    @Singleton
    fun provideContext(app: App) : Context {
        return app.applicationContext
    }
}
