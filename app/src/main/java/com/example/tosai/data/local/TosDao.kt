package com.example.tosai.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TosDao {
    @Insert
    suspend fun insert(analysis: ContractAnalysisEntity)

    @Delete
    suspend fun delete(analysis: ContractAnalysisEntity)

    @Query("SELECT * FROM contract_analyses ORDER BY id DESC")
    fun getAllAnalyses(): Flow<List<ContractAnalysisEntity>>
}