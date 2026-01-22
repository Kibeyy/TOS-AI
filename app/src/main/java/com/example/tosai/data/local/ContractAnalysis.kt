package com.example.tosai.data.local

import kotlinx.serialization.Serializable

@Serializable
data class ContractAnalysis(
    val keyFindings: List<KeyFinding>,
    val riskPercentage: Int,
    val title: String
)