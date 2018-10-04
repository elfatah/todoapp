package elfatahwashere.com.todoapp.di

import elfatahwashere.com.todoapp.ToDoViewModel
import elfatahwashere.com.todoapp.repo.TodoRepository
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val activityModule = module {

    viewModel { ToDoViewModel(get()) }

    single { TodoRepository(get(), get()) }
}
