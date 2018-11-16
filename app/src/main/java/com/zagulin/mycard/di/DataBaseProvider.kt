package com.zagulin.mycard.di

import android.content.Context
import androidx.room.Room
import com.zagulin.mycard.db.AppDatabase
import toothpick.ProvidesSingletonInScope
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton


@Singleton
@ProvidesSingletonInScope
class DataBaseProvider : Provider<AppDatabase> {
    @Inject
    lateinit var context: Context

    override fun get(): AppDatabase = Room
            .databaseBuilder(context.applicationContext, AppDatabase::class.java, "ny_times_db")

            .fallbackToDestructiveMigration()
            .build()


}