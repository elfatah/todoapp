package elfatahwashere.com.todoapp


import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_to_do_detail.*


class ToDoDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_to_do_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val toDoViewModel =
            ViewModelProviders.of(activity!!).get(ToDoViewModel::class.java)
        val toDo = ToDoDetailFragmentArgs.fromBundle(arguments).toDoArgs
        toDo?.let {
            edToDo.setText(it.name)
        }

        btnAdd.setOnClickListener {
            if (edToDo.text.isNotBlank()) {
                toDo?.let {
                    toDoViewModel.update(ToDo(id = it.id, name = edToDo.text.toString()))
                } ?: toDoViewModel.add(ToDo(name = edToDo.text.toString()))

                Navigation.findNavController(activity!!, R.id.my_nav_host_fragment).navigateUp()
            }
        }
    }
}
