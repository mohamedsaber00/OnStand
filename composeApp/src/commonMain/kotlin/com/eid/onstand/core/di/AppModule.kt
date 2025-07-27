package com.eid.onstand.core.di

import com.eid.onstand.core.data.CustomizationDataSource
import com.eid.onstand.core.data.SettingsRepository
import com.eid.onstand.core.data.createDataStore
import com.eid.onstand.feature.customization.CustomizationViewModel
import com.eid.onstand.feature.home.HomeScreenViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { createDataStore() }
    single { CustomizationDataSource(get()) }
    single { SettingsRepository(get()) }

    viewModel { CustomizationViewModel(get()) }
    viewModel { HomeScreenViewModel(get()) }
}