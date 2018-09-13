package elfatahwashere.com.todoapp

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList

/**
 * Created by hilman on 12/09/2018.
 */
class ToDoViewModel(application: Application) : AndroidViewModel(application) {

    private val dbase = AppDatabase.getAppDatabase(application)
    private val toDoDao = dbase.todoDao()

    var todoLiveData = MutableLiveData<ToDo>()

    fun add(toDo: ToDo) = ioThread { toDoDao.addToDo(toDo) }

    fun delete(toDo: ToDo) = ioThread { toDoDao.deleteToDo(toDo) }

    fun update(toDo: ToDo) = ioThread { toDoDao.editTodo(toDo) }


    val allTodo = LivePagedListBuilder(
        toDoDao.getAllToDos(), PagedList.Config.Builder()
            .setPageSize(10)
            .setEnablePlaceholders(false)
            .build()
    ).build()
}
