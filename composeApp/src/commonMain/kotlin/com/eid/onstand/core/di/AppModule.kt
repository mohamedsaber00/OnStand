package com.eid.onstand.core.di

import com.eid.onstand.feature.preview.PreviewViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { PreviewViewModel() }
}