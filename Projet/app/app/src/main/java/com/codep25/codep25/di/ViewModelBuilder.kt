package com.codep25.codep25.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.codep25.codep25.viewmodel.*
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelBuilder {

    @Binds
    internal abstract fun binViewModelFactory(factory: BaseViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    internal abstract fun splashViewModel(viewModel: SplashViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    internal abstract fun homeViewModel(viewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ParticipantsViewModel::class)
    internal abstract fun participantsViewModel(viewModel: ParticipantsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TeamManagementViewModel::class)
    internal abstract fun teamManagementViewModel(viewModel: TeamManagementViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RaceViewModel::class)
    internal abstract fun raceViewModel(viewModel: RaceViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RacesResultsViewModel::class)
    internal abstract fun racesResultsViewModel(viewModel: RacesResultsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RaceResultsViewModel::class)
    internal abstract fun raceResultsViewModel(viewModel: RaceResultsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    internal abstract fun settingsViewModel(viewModel: SettingsViewModel): ViewModel
}
