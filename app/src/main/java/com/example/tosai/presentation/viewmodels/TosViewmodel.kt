package com.example.tosai.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tosai.data.local.ContractAnalysis
import com.example.tosai.data.repo.TosRepository
//import com.google.ai.client.generativeai.BuildConfig
//You
import com.google.ai.client.generativeai.GenerativeModel
import com.example.tosai.BuildConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import javax.inject.Inject

// ---------------- UI STATE ----------------
sealed class AnalysisState {
    object Idle : AnalysisState()
    object Loading : AnalysisState()

    data class Success(
        val analysis: ContractAnalysis
    ) : AnalysisState()

    data class Error(val message: String) : AnalysisState()
}

@HiltViewModel
class TosViewModel @Inject constructor(private val repository: TosRepository) : ViewModel() {


    fun resetState() {
        _uiState.value = AnalysisState.Idle
    }
    val apiKey = BuildConfig.API_KEY

    private val model = GenerativeModel(
        modelName = "gemini-3-flash-preview",
        apiKey = apiKey
    )

    val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    private val _uiState = MutableStateFlow<AnalysisState>(AnalysisState.Idle)
    val uiState = _uiState.asStateFlow()

    val allAnalyses: Flow<List<ContractAnalysis>> = repository.getAllAnalyses()
    fun deleteAnalysis(analysis: ContractAnalysis){
        viewModelScope.launch{
            repository.deleteAnalysis(
              analysis
            )

        }
    }

    fun analyzeContract(contractText: String) {
        if (contractText.isBlank()) {
            _uiState.value = AnalysisState.Error("Contract text cannot be empty")
            return
        }

        _uiState.value = AnalysisState.Loading

        viewModelScope.launch {
            var attempts = 0
            val maxAttempts = 3

            while (attempts < maxAttempts) {
                try {
                    val prompt = """
You are TOS AI, the best terms of service analyzer.

Return ONLY valid JSON in this exact structure:

{
  "title": "string",
  "riskPercentage": number,
  "keyFindings": [
    {
      "title": "string",
      "description": "string",
      "severity": "HIGH|MEDIUM|LOW|GOOD"
    }
  ]
}

Rules:
- severity must be exactly one of: HIGH, MEDIUM, LOW, GOOD
- riskPercentage must be 0-100
- Return valid JSON only
- No markdown, no explanations
- Start with { and end with }

Text to analyze:
$contractText
                """.trimIndent()

                    // Call Gemini API
                    val response = model.generateContent(prompt)
                    val responseText = response.text ?: throw Exception("Empty response from AI")

                    // Clean the response
                    val cleanedJson = responseText
                        .trim()
                        .removePrefix("```json")
                        .removePrefix("```")
                        .removeSuffix("```")
                        .trim()

                    // Parse JSON to Kotlin object
                    val analysisResult = json.decodeFromString<ContractAnalysis>(cleanedJson)

                    // Save to database
                    repository.saveToDatabase(analysisResult)

                    // Update UI with success
                    _uiState.value = AnalysisState.Success(analysisResult)

                    return@launch // Success - exit function

                } catch (e: Exception) {
                    attempts++

                    if (attempts >= maxAttempts) {
                        // All retries failed - show error
                        val errorMessage = when {
                            e.message?.contains("overloaded") == true ||
                                    e.message?.contains("503") == true ->
                                "Gemini is busy. Please try again in a moment."
                            e.message?.contains("Serializer") == true ->
                                "Failed to parse response. Please try again."
                            else -> e.message ?: "Something went wrong"
                        }
                        _uiState.value = AnalysisState.Error(errorMessage)
                    } else {
                        // Wait before retrying
                        delay(2000) // 2 second delay
                    }
                }
            }
        }
    }



}















//                // Call AI
//                val response = model.generateContent(prompt)
//                val json = response.text ?: throw Exception("AI returned no text")
//
//                // Parse JSON
//                val aiResponse = Gson().fromJson(json, AiResponse::class.java)
//
//                // Map to UI models
//                val findings = mapAiFindings(aiResponse.keyFindings)
//
//                // Emit success
//                _uiState.value = AnalysisState.Success(
//                    title = response,
//                    riskPercentage = aiResponse.riskPercentage,
//                    findings = findings
//                )