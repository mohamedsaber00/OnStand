package com.eid.onstand.core.di

import com.eid.onstand.core.data.BackgroundRepository
import com.eid.onstand.core.data.ClockRepository
import com.eid.onstand.core.data.CustomizationRepository
import com.eid.onstand.feature.preview.PreviewViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { CustomizationRepository(
        backgroundRepository = BackgroundRepository(),
        clockRepository = ClockRepository()
    ) }
    viewModel { PreviewViewModel(get()) }
}