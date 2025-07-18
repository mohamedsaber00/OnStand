package com.eid.onstand.core.di

import com.eid.onstand.AppViewModel
import com.eid.onstand.core.data.BackgroundRepository
import com.eid.onstand.core.data.ClockRepository
import com.eid.onstand.core.data.CustomizationRepository
import com.eid.onstand.core.data.CustomizationDataSource
import com.eid.onstand.core.data.createDataStore
import com.eid.onstand.feature.preview.PreviewViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { createDataStore() }
    single { CustomizationDataSource(get()) }
    single { BackgroundRepository() }
    single { ClockRepository() }
    single { CustomizationRepository(get(), get(), get()) }
    viewModel { AppViewModel(get()) }
    viewModel { PreviewViewModel(get()) }
}