package com.example.tosai.data.repo


import com.example.tosai.data.local.ContractAnalysis
import com.example.tosai.data.local.ContractAnalysisEntity
import com.example.tosai.data.local.KeyFinding
import com.example.tosai.data.local.TosDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class TosRepository@Inject constructor(private val dao: TosDao) {

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    // Save analysis from Gemini to database
    suspend fun saveToDatabase(analysis: ContractAnalysis) {
        val entity = ContractAnalysisEntity(
            title = analysis.title,
            riskPercentage = analysis.riskPercentage,
            keyFindingsJson = Json.encodeToString(analysis.keyFindings)
        )
        dao.insert(entity)
    }
    //delete
    suspend fun deleteAnalysis(analysis: ContractAnalysis){
        val entity = ContractAnalysisEntity(

            title = analysis.title,
            riskPercentage = analysis.riskPercentage,
            keyFindingsJson = Json.encodeToString(analysis.keyFindings)
        )
        dao.delete(entity)
    }

    // Get all analyses from database
    fun getAllAnalyses(): Flow<List<ContractAnalysis>> {
        return dao.getAllAnalyses().map { entities ->
            entities.map { entity ->
                // Convert each entity back to ContractAnalysis
                ContractAnalysis(
                    title = entity.title,
                    riskPercentage = entity.riskPercentage,
                    keyFindings = json.decodeFromString<List<KeyFinding>>(entity.keyFindingsJson)
                )
            }
        }
    }
}