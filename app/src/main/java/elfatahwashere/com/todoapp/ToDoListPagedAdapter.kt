package elfatahwashere.com.todoapp

import android.arch.paging.PagedListAdapter
import android.databinding.DataBindingUtil
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import elfatahwashere.com.todoapp.data.ToDoRaw
import elfatahwashere.com.todoapp.databinding.TodoItemViewBinding

/**
 * Created by hilman on 13/09/2018.
 */
class ToDoListPagedAdapter(val onClick: (ToDoRaw) -> Unit) :
        PagedListAdapter<ToDoRaw, ToDoViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate<TodoItemViewBinding>(
                layoutInflater,
                R.layout.todo_item_view,
                parent,
                false
            )
        return ToDoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        holder.bindings.toDo = getItem(position)
        holder.itemView.setOnClickListener {
            onClick(getItem(position)!!)
        }

    }

    companion object {
        /**
         * This diff callback informs the PagedListAdapter how to compute list differences when new
         * PagedLists arrive.
         * <p>
         * When you add a Cheese with the 'Add' button, the PagedListAdapter uses diffCallback to
         * detect there's only a single item difference from before, so it only needs to animate and
         * rebind a single view.
         *
         * @see android.support.v7.util.DiffUtil
         */
        private val diffCallback = object : DiffUtil.ItemCallback<ToDoRaw>() {
            override fun areItemsTheSame(oldItem: ToDoRaw, newItem: ToDoRaw): Boolean =
                oldItem.id == newItem.id

            /**
             * Note that in kotlin, == checking on data classes compares all contents, but in Java,
             * typically you'll implement Object#equals, and use it to compare object contents.
             */
            override fun areContentsTheSame(oldItem: ToDoRaw, newItem: ToDoRaw): Boolean =
                oldItem == newItem
        }
    }
}

class ToDoViewHolder(val bindings: TodoItemViewBinding) :
    RecyclerView.ViewHolder(bindings.root)
