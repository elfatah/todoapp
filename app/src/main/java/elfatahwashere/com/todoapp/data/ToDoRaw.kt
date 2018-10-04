package elfatahwashere.com.todoapp.data

data class ToDoRaw(val id: Int, val name: String)

fun ToDoRaw.toEntity() =
        with(this) {
            ToDo(id, name)
        }