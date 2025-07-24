package com.eid.onstand.core

import com.eid.onstand.core.models.BackgroundRegistry
import com.eid.onstand.core.models.ClockRegistry
import com.eid.onstand.core.modules.CoreModule
import com.eid.onstand.core.modules.FeatureModule

/**
 * Initializes the application by registering all feature modules.
 */
object AppInitializer {
    private val modules: List<FeatureModule> = listOf(
        CoreModule()
    )
    
    /**
     * Initializes all modules and registers their components.
     */
    fun initialize() {
        // Clear any existing registrations
        BackgroundRegistry.clear()
        ClockRegistry.clear()
        
        // Register all module components
        modules.forEach { module ->
            module.registerBackgrounds(BackgroundRegistry)
            module.registerClocks(ClockRegistry)
            module.initialize()
        }
        
        // Verify registrations
        println("Initialized ${BackgroundRegistry.getAll().size} backgrounds")
        println("Initialized ${ClockRegistry.getAll().size} clocks")
    }
}