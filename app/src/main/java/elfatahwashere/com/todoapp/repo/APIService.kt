package elfatahwashere.com.todoapp.repo

import elfatahwashere.com.todoapp.data.ToDoRaw
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface APIService {
    @GET("/todo/api.php")
    fun getTodos(@Query("rowsperpage") rowsPerPage: Int,
                 @Query("currentpage") currentPage: Int): Single<List<ToDoRaw>>

    @POST("/todo/delete.php")
    fun deleteTodo(@Query("id") id: Int): Completable

    @POST("/todo/update.php")
    fun updateTodo(@Query("id") id: Int,
                   @Query("name") name: String): Single<ToDoRaw>

    @POST("/todo/insert.php")
    fun insertTodo(@Query("name") name: String): Single<ToDoRaw>

}