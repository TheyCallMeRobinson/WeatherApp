package cs.vsu.ru.application.ui.drawer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import cs.vsu.ru.application.databinding.FragmentDrawerBinding

class DrawerFragment : Fragment() {
    private lateinit var binding: FragmentDrawerBinding
    private lateinit var viewModel: DrawerViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDrawerBinding.inflate(inflater)
        viewModel = ViewModelProvider(this).get(DrawerViewModel::class.java)

        return binding.root
    }
}