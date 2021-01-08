package com.codep25.codep25.di

import com.codep25.codep25.ui.fragment.*
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuilder {

    @ContributesAndroidInjector
    abstract fun bindHomeFragment() : HomeFragment

    @ContributesAndroidInjector
    abstract fun bindParticipantsFragment() : ParticipantsFragment

    @ContributesAndroidInjector
    abstract fun bindTeamManagementFragment() : TeamManagementFragment

    @ContributesAndroidInjector
    abstract fun bindRaceFragment() : RaceFragment

    @ContributesAndroidInjector
    abstract fun bindRacesResultsFragment() : RacesResultsFragment

    @ContributesAndroidInjector
    abstract fun bindSettingsFragment() : SettingsFragment
}
