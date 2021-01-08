package com.codep25.codep25.di

import com.codep25.codep25.ui.activity.MainActivity
import com.codep25.codep25.ui.activity.RaceResultsActivity
import com.codep25.codep25.ui.activity.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector
    abstract fun bindingSplashActivity() : SplashActivity

    @ContributesAndroidInjector
    abstract fun bindingMainActivity() : MainActivity

    @ContributesAndroidInjector
    abstract fun bindingRaceResultsActivity() : RaceResultsActivity
}
