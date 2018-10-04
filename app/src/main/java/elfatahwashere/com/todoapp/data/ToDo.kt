package elfatahwashere.com.todoapp.data

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by hilman on 12/09/2018.
 */
@Entity(tableName = "ToDo")
@Parcelize
data class ToDo(
        @PrimaryKey(autoGenerate = true) val id: Int = 0,
        val name: String
) : Parcelable

fun ToDo.toRaw() =
        with(this) {
            ToDoRaw(id, name)
        }
