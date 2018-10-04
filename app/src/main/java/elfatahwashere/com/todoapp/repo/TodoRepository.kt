package elfatahwashere.com.todoapp.repo

import android.arch.paging.PagedList
import android.arch.paging.RxPagedListBuilder
import elfatahwashere.com.todoapp.AppDatabase
import elfatahwashere.com.todoapp.data.ToDoRaw
import elfatahwashere.com.todoapp.data.toEntity
import elfatahwashere.com.todoapp.data.toRaw
import io.reactivex.Observable
import io.reactivex.Single

class TodoRepository(appDatabase: AppDatabase, apiService: APIService) {
    private val dao = appDatabase.todoDao()
    private val apiService = apiService
    fun getAllToDos(currentPage: Int): Observable<PagedList<ToDoRaw>> = apiService.getTodos(rowsPerPage = 20, currentPage = currentPage)
            .flatMap { storeTodo(it) }.ignoreElement().andThen(
                    RxPagedListBuilder(dao.getAllToDos().map { it.toRaw() }, 10)
                            .buildObservable()
            )

    fun insertToDo(todo: ToDoRaw) =
            apiService.insertTodo(todo.name)
                    .doOnSuccess {
                        dao.insertToDo(it.toEntity())
                    }


    fun editToDo(todo: ToDoRaw) =
            apiService.updateTodo(todo.id, todo.name)
                    .doOnSuccess {
                        dao.editTodo(it.toEntity())
                    }


    fun deleteToDo(todo: ToDoRaw) =
            apiService.deleteTodo(todo.id)
                    .doOnComplete {
                        dao.deleteToDo(todo.toEntity())
                    }


    fun storeTodo(rawTodos: List<ToDoRaw>): Single<List<ToDoRaw>> {
        return Single.just(rawTodos.map {
            dao.insertToDo(it.toEntity())
            it
        })
    }

}