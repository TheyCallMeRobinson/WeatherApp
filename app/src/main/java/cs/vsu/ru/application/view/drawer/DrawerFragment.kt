package cs.vsu.ru.application.view.drawer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import cs.vsu.ru.application.databinding.FragmentDrawerBinding
import cs.vsu.ru.application.viewmodel.DrawerViewModel
import cs.vsu.ru.environment.Resource
import cs.vsu.ru.environment.Status
import org.koin.androidx.viewmodel.ext.android.viewModel

class DrawerFragment : Fragment() {
    private lateinit var binding: FragmentDrawerBinding
    private val viewModel by viewModel<DrawerViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDrawerBinding.inflate(inflater)

        setUpObservers()

        return binding.root
    }

    private fun setUpObservers() {
        viewModel.getFavoriteLocation().observe(viewLifecycleOwner) {
            it?.let {
                when(it.status) {
                    Status.SUCCESS -> {
                        binding.drawerFavoritePlace.itemLocationName.text = it.data?.name
                        Toast.makeText(context, "Success", Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        Toast.makeText(context, "Loading", Toast.LENGTH_LONG).show()
                    }
                    Status.ERROR -> {
                        Toast.makeText(context, "Error", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}
