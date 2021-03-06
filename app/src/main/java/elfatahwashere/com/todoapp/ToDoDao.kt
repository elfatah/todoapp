package elfatahwashere.com.todoapp

import android.arch.paging.DataSource
import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import elfatahwashere.com.todoapp.data.ToDo


/**
 * Created by hilman on 12/09/2018.
 */
@Dao
interface ToDoDao {
    @Query("select * from ToDo order by id desc")
    fun getAllToDos(): DataSource.Factory<Int, ToDo>

    @Query("select * from TODO where id = :id")
    fun getItemById(id: Int): ToDo

    @Insert(onConflict = REPLACE)
    fun insertToDo(toDo: ToDo)

    @Update
    fun editTodo(toDo: ToDo)

    @Insert(onConflict = REPLACE)
    fun insertToDo(toDos: List<ToDo>)

    @Delete
    fun deleteToDo(toDo: ToDo)
}
