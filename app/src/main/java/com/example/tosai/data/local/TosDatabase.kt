package com.example.tosai.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ContractAnalysisEntity::class], version = 1, exportSchema = true)
abstract class TosDatabase: RoomDatabase() {
    abstract val dao: TosDao
}