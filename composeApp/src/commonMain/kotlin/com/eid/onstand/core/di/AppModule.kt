package com.eid.onstand.core.di

import com.eid.onstand.core.data.CustomizationDataSource
import com.eid.onstand.core.data.DashboardDataSource
import com.eid.onstand.core.data.DashboardRepository
import com.eid.onstand.core.data.SettingsRepository
import com.eid.onstand.core.data.createDataStore
import com.eid.onstand.feature.customization.CustomizationViewModel
import com.eid.onstand.feature.dashboard.DashboardViewModel
import com.eid.onstand.feature.home.HomeScreenViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { createDataStore() }
    single { CustomizationDataSource(get()) }
    single { DashboardDataSource(get()) }
    single { SettingsRepository(get()) }
    single { DashboardRepository(get()) }

    viewModel { CustomizationViewModel(get()) }
    viewModel { HomeScreenViewModel(get(), get()) }
    viewModel { DashboardViewModel(get()) }
}