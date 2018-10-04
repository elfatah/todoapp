package elfatahwashere.com.todoapp


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import elfatahwashere.com.todoapp.data.toEntity
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.fragment_todo_list.*
import kotlinx.android.synthetic.main.refreshable_list.view.*
import org.koin.android.viewmodel.ext.android.sharedViewModel


class TodoListFragment : Fragment() {
    lateinit var toDoListAdapter: ToDoListPagedAdapter
    val toDoViewModel: ToDoViewModel by sharedViewModel()
    var currentPage = 0
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_todo_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        toDoViewModel =
//                ViewModelProviders.of(activity!!).get(ToDoViewModel::class.java)
        toDoListAdapter = ToDoListPagedAdapter {
            val action = TodoListFragmentDirections.actionTodoListFragmentToToDoDetailFragment(it.toEntity())
            Navigation.findNavController(activity!!, R.id.my_nav_host_fragment).navigate(action)
        }

        initRecyclerView()


        initSwipeToDelete()
        initView()
        loadData(currentPage)
    }

    private fun loadData(page: Int) {
        toDoViewModel.getAllTodo(page).doOnSubscribe {
            rvTodo.isRefreshing = true
        }.doOnTerminate { rvTodo.isRefreshing = false }
                .subscribeBy(onNext = {
                    toDoListAdapter.submitList(it)
                }, onError = {
                    Log.e("error", it.localizedMessage)
                })


    }

    private fun initRecyclerView() {
        rvTodo.recyclerView.adapter = toDoListAdapter
        rvTodo.setOnRefreshListener {
            currentPage = 0
            loadData(currentPage)
        }
        rvTodo.setOnLoadMoreListener(object : RefreshableList.OnLoadMoreListener {
            override fun onLoadMore() {
                currentPage++
                loadData(currentPage)

            }

        })
    }

    private fun initView() {
        fabAddTodo.setOnClickListener {
            val action = TodoListFragmentDirections.actionTodoListFragmentToToDoDetailFragment(null)
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

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                (viewHolder as? ToDoViewHolder)?.bindings?.toDo?.let {
                    toDoViewModel.delete(it).subscribeBy { }
                }
            }
        }).attachToRecyclerView(rvTodo.recyclerView)
    }

}
