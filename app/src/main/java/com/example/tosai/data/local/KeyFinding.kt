package com.example.tosai.data.local

import kotlinx.serialization.Serializable

@Serializable
data class KeyFinding(
    val description: String,
    val severity: Severity,
    val title: String
)

@Serializable
enum class Severity {
    HIGH,      // Critical issues
    MEDIUM,    // Moderate concerns
    LOW,       // Minor issues
    GOOD       // Favorable terms
}