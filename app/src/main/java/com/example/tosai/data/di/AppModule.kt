package com.example.tosai.data.di

import android.content.Context
import androidx.room.Room
import com.example.tosai.data.local.TosDao
import com.example.tosai.data.local.TosDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): TosDatabase{
        return Room.databaseBuilder(
            context,
            TosDatabase::class.java,
            "tos_database"
        ).build()
    }

    @Singleton
    @Provides
    fun provideDao(db: TosDatabase): TosDao{
        return db.dao
    }



}