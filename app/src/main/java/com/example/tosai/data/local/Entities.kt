package com.example.tosai.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contract_analyses")
class ContractAnalysisEntity (
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    val title:String,
    val riskPercentage: Int,
    val keyFindingsJson: String

)