package com.eid.onstand.core.di

import com.eid.onstand.AppViewModel
import com.eid.onstand.core.data.BackgroundRepository
import com.eid.onstand.core.data.ClockRepository
import com.eid.onstand.core.data.CustomizationRepository
import com.eid.onstand.core.data.UserPreferencesRepository
import com.eid.onstand.feature.preview.PreviewViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { BackgroundRepository() }
    single { ClockRepository() }
    single { UserPreferencesRepository(get(), get()) }
    single { CustomizationRepository(get(), get(), get()) }
    viewModel { AppViewModel(get()) }
    viewModel { PreviewViewModel(get()) }
}