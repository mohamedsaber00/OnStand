package com.eid.onstand.core.data

import kotlinx.serialization.Serializable

@Serializable
data class SerializableCustomizationState(
    val backgroundId: String? = null,
    val backgroundType: String? = null,
    val clockTypeId: String? = null,
    val clockTypeName: String? = null,
    val selectedFont: String? = null,
    val selectedColorName: String? = null
)
