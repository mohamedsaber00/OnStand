package com.eid.onstand.navigation

import androidx.navigation3.runtime.NavKey
import androidx.savedstate.serialization.SavedStateConfiguration
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

@Serializable
sealed interface Route : NavKey {
    @Serializable
    data object Home : Route

    @Serializable
    data object Customization : Route

    @Serializable
    data object Dashboard : Route

    @Serializable
    data object Back : Route
}

val navConfig = SavedStateConfiguration {
    serializersModule = SerializersModule {
        polymorphic(NavKey::class) {
            subclass(Route.Home::class, Route.Home.serializer())
            subclass(Route.Customization::class, Route.Customization.serializer())
            subclass(Route.Dashboard::class, Route.Dashboard.serializer())
            subclass(Route.Back::class, Route.Back.serializer())
        }
    }
}
