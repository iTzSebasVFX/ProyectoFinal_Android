package com.example.proyectofinal_recetas.dataRoom.di

import android.content.Context
import androidx.room.Room
import com.example.proyectofinal_recetas.dataRoom.dao.RecetaDao
import com.example.proyectofinal_recetas.dataRoom.dao.UsuarioDao
import com.example.proyectofinal_recetas.dataRoom.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "usuario_receta_db"
        ).build()
    }

    @Provides
    fun provideUsuarioDao(db: AppDatabase): UsuarioDao {
        return db.UsuarioDao()
    }

    @Provides
    fun provideRecetaDao(db: AppDatabase): RecetaDao {
        return db.RecetaDao()
    }
}