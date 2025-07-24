package com.eid.onstand.core.di

import com.eid.onstand.core.data.CustomizationDataSource
import com.eid.onstand.core.data.createDataStore
import org.koin.dsl.module

val appModule = module {
    single { createDataStore() }
    single { CustomizationDataSource(get()) }
}