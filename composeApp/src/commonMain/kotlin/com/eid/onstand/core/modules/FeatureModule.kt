package com.eid.onstand.core.modules

import com.eid.onstand.core.models.BackgroundRegistry
import com.eid.onstand.core.models.ClockRegistry

/**
 * Interface for feature modules that can register backgrounds and clocks.
 */
interface FeatureModule {
    /**
     * Registers background effects provided by this module.
     */
    fun registerBackgrounds(registry: BackgroundRegistry) {}
    
    /**
     * Registers clock widgets provided by this module.
     */
    fun registerClocks(registry: ClockRegistry) {}
    
    /**
     * Initializes the module. Called after registration.
     */
    fun initialize() {}
}