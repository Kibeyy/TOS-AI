package com.example.tosai.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class SimpleAnalysisEntity (
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    val scannedItemTitle:String,
    val dateScanned:String,
    val riskScore: Int
)