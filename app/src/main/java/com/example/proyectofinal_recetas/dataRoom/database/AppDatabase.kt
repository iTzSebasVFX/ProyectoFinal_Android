package com.example.proyectofinal_recetas.dataRoom.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.proyectofinal_recetas.dataRoom.dao.RecetaDao
import com.example.proyectofinal_recetas.dataRoom.dao.UsuarioDao
import com.example.proyectofinal_recetas.dataRoom.local.RecetaLocal
import com.example.proyectofinal_recetas.dataRoom.local.UsuarioLocal

@Database(entities = [UsuarioLocal::class, RecetaLocal::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun UsuarioDao(): UsuarioDao
    abstract fun RecetaDao(): RecetaDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "usuario_receta_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}