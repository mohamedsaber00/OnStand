package com.eid.onstand.core.di

import org.koin.core.module.Module
import org.koin.dsl.module

actual fun getPlatformModule(): Module = module {
    // No additional dependencies needed for iOS
}