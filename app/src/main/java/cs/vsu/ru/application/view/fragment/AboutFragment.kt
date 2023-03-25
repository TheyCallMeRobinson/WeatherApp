package cs.vsu.ru.application.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import cs.vsu.ru.application.R
import cs.vsu.ru.application.databinding.AboutApplicationBinding

class AboutFragment : Fragment() {

    private lateinit var binding: AboutApplicationBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AboutApplicationBinding.inflate(inflater)

        val transitionInflater = TransitionInflater.from(requireContext())
        enterTransition = transitionInflater.inflateTransition(R.transition.slide_to_left)
        exitTransition = transitionInflater.inflateTransition(R.transition.slide_to_right)

        binding.fragmentBackBtn.setOnClickListener {
            navigateBack()
        }

        return binding.root
    }

    private fun navigateBack() {
        findNavController().navigate(R.id.action_aboutFragment_to_drawerFragment)
    }
}