package elfatahwashere.com.todoapp

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

/**
 * Created by hilman on 12/09/2018.
 */
@Entity(tableName = "ToDo")
@Parcelize
data class ToDo(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String
) : Parcelable
