package elfatahwashere.com.todoapp.di

import android.arch.persistence.room.Room
import android.content.Context
import elfatahwashere.com.todoapp.AppDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.module

val databaseModule = module {
    single { provideRoomDatabase(androidApplication()) }
}

fun provideRoomDatabase(context: Context): AppDatabase {
    return Room.databaseBuilder(context, AppDatabase::class.java, "app-database")
            .setJournalMode(android.arch.persistence.room.RoomDatabase.JournalMode.TRUNCATE)
            .build()
}
