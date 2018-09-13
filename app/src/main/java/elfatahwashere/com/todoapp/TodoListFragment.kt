package elfatahwashere.com.todoapp


import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_todo_list.*


class TodoListFragment : Fragment() {
    lateinit var toDoListAdapter: ToDoListPagedAdapter
    lateinit var toDoViewModel: ToDoViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_todo_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        toDoViewModel =
                ViewModelProviders.of(activity!!).get(ToDoViewModel::class.java)
        toDoListAdapter = ToDoListPagedAdapter {
            val action = TodoListFragmentDirections.actionTodoListFragmentToToDoDetailFragment()
            action.setToDoArgs(it)
            Navigation.findNavController(activity!!, R.id.my_nav_host_fragment).navigate(action)
        }

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = toDoListAdapter
        toDoViewModel.allTodo.observe(this, android.arch.lifecycle.Observer {
            toDoListAdapter.submitList(it)
        })

        initSwipeToDelete()
        initView()
    }

    private fun initView() {
        fabAddTodo.setOnClickListener {
            val action = TodoListFragmentDirections.actionTodoListFragmentToToDoDetailFragment()
            action.setToDoArgs(null)
            Navigation.findNavController(activity!!, R.id.my_nav_host_fragment).navigate(action)
        }
    }

    private fun initSwipeToDelete() {
        ItemTouchHelper(object : ItemTouchHelper.Callback() {
            // enable the items to swipe to the left or right
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int =
                makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)

            override fun onMove(
                recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            // When an item is swiped, remove the item via the view model. The list item will be
            // automatically removed in response, because the adapter is observing the live list.
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int) {
                (viewHolder as? ToDoViewHolder)?.bindings?.toDo?.let {
                    toDoViewModel.delete(it)
                }
            }
        }).attachToRecyclerView(recyclerView)
    }

}
