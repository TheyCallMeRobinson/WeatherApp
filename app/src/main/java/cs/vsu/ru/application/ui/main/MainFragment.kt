package cs.vsu.ru.application.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import cs.vsu.ru.application.R
import cs.vsu.ru.application.databinding.FragmentContentMainBinding

class MainFragment : Fragment() {

    private lateinit var binding: FragmentContentMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContentMainBinding.inflate(inflater)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        connectFragmentToViewModel()

        binding.mainToolbarBurger.setOnClickListener {
            activity?.findViewById<DrawerLayout>(R.id.activity_main_layout)?.openDrawer(GravityCompat.START)
        }

        val textView: TextView? = activity?.findViewById<TextView>(R.id.main_temperature_now_tv)
        textView?.text = "65"

        return binding.root
    }

    private fun connectFragmentToViewModel() {
        viewModel.backgroundResource.observe(viewLifecycleOwner) {
            binding.mainBackgroundImg.setBackgroundResource(it)
        }
        binding.mainBackgroundImg.setAltImageResource(R.color.black)
    }
}