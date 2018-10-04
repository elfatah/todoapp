package elfatahwashere.com.todoapp

import android.arch.lifecycle.ViewModel
import elfatahwashere.com.todoapp.data.ToDoRaw
import elfatahwashere.com.todoapp.repo.TodoRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by hilman on 12/09/2018.
 */
class ToDoViewModel(val todoRepository: TodoRepository) : ViewModel() {

    fun add(toDo: ToDoRaw) = todoRepository.insertToDo(toDo)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun delete(toDo: ToDoRaw) = todoRepository.deleteToDo(toDo)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun update(toDo: ToDoRaw) = todoRepository.editToDo(toDo)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun getAllTodo(page: Int) = todoRepository.getAllToDos(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}
