package elfatahwashere.com.todoapp


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import elfatahwashere.com.todoapp.data.ToDoRaw
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.fragment_to_do_detail.*
import org.koin.android.viewmodel.ext.android.sharedViewModel


class ToDoDetailFragment : Fragment() {
    private val toDoViewModel:
            ToDoViewModel by sharedViewModel()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_to_do_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val toDo = ToDoDetailFragmentArgs.fromBundle(arguments).toDoArgs
        toDo?.let {
            edToDo.setText(it.name)
        }

        btnAdd.setOnClickListener {
            if (edToDo.text.isNotBlank()) {
                toDo?.let {
                    toDoViewModel.update(ToDoRaw(id = it.id, name = edToDo.text.toString()))
                            .subscribeBy({}, {
                                Navigation.findNavController(activity!!, R.id.my_nav_host_fragment).navigateUp()

                            })
                }
                        ?: toDoViewModel.add(ToDoRaw(id = 0, name = edToDo.text.toString())).subscribeBy({}, {
                            Navigation.findNavController(activity!!, R.id.my_nav_host_fragment).navigateUp()

                        })

            }
        }
    }
}


