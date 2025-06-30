package com.example.proyectofinal_recetas.dataAPI.di

import com.example.proyectofinal_recetas.dataAPI.remote.RecetaApi
import com.example.proyectofinal_recetas.dataAPI.remote.UsuarioApi
import com.example.proyectofinal_recetas.dataRoom.dao.UsuarioDao
import com.example.proyectofinal_recetas.repository.UsuarioLocalRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideBaseUrl(): String = "http://10.0.2.2:3000/"

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: String): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideUsuarioApi(retrofit: Retrofit): UsuarioApi =
        retrofit.create(UsuarioApi::class.java)

    @Provides
    @Singleton
    fun provideRecetaApi(retrofit: Retrofit): RecetaApi =
        retrofit.create(RecetaApi::class.java)
}