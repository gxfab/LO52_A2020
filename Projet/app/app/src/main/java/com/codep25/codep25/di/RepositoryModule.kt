package com.codep25.codep25.di

import android.content.Context
import android.content.SharedPreferences
import com.codep25.codep25.model.color.ParticipantColors
import com.codep25.codep25.model.storage.DataBase
import com.codep25.codep25.model.storage.SharedPreferencesStorage
import com.codep25.codep25.model.storage.Storage
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Suppress("unused")
@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideParticipantColorsFactory(storage: Storage) : ParticipantColors.Factory {
        return ParticipantColors.Factory(storage)
    }

    @Singleton
    @Provides
    fun provideSharedPreference(ctx: Context) : SharedPreferences {
        return ctx.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    }

    @Singleton
    @Provides
    fun provideStorage(pref: SharedPreferences) : Storage {
        return SharedPreferencesStorage(pref)
    }

    @Singleton
    @Provides
    fun provideDatabase(appCtx: Context): DataBase {
        return DataBase(appCtx)
    }
}
