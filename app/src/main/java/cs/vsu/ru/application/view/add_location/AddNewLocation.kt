package cs.vsu.ru.application.view.add_location

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cs.vsu.ru.application.R
import cs.vsu.ru.application.databinding.FragmentAddNewLocationBinding
import cs.vsu.ru.application.viewmodel.AddNewLocationViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddNewLocation : Fragment() {

    private lateinit var binding: FragmentAddNewLocationBinding
    private val viewModel by viewModel<AddNewLocationViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddNewLocationBinding.inflate(inflater)

        return inflater.inflate(R.layout.fragment_add_new_location, container, false)
    }

}
