package cs.vsu.ru.application.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import cs.vsu.ru.application.R
import cs.vsu.ru.application.databinding.FragmentRouteBinding

class RouteFragment : Fragment() {

    private lateinit var binding: FragmentRouteBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRouteBinding.inflate(inflater)

        val transitionInflater = TransitionInflater.from(requireContext())
        enterTransition = transitionInflater.inflateTransition(R.transition.slide_to_bottom)
        exitTransition = transitionInflater.inflateTransition(R.transition.slide_to_top)

        binding.toFragmentMainBtn.setOnClickListener {
            navigateBack()
        }

        binding.mainToolbarBurger.setOnClickListener {
            activity?.findViewById<DrawerLayout>(R.id.activity_main_layout)?.openDrawer(
                GravityCompat.START)
        }

        return binding.root
    }

    private fun navigateBack() {
        findNavController().navigateUp()
    }
}