package elfatahwashere.com.todoapp

import android.app.Application
import android.content.Context
import elfatahwashere.com.todoapp.di.activityModule
import elfatahwashere.com.todoapp.di.apiModule
import elfatahwashere.com.todoapp.di.databaseModule
import org.koin.android.ext.android.startKoin


class TodoApp : Application() {
    companion object {
        lateinit var appContext: Context

    }

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(apiModule, databaseModule, activityModule))
        appContext = this
    }
}