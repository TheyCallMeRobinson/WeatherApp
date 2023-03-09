package cs.vsu.ru.application.view.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import org.koin.androidx.viewmodel.ext.android.viewModel
import cs.vsu.ru.application.R
import cs.vsu.ru.application.databinding.ContentMainHeaderBinding
import cs.vsu.ru.application.databinding.FragmentContentMainBinding
import cs.vsu.ru.application.model.TemperatureModel
import cs.vsu.ru.application.viewmodel.MainViewModel
import cs.vsu.ru.domain.model.weather.Weather
import cs.vsu.ru.environment.Status

class MainFragment : Fragment() {

    private lateinit var binding: FragmentContentMainBinding
    private val viewModel by viewModel<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContentMainBinding.inflate(inflater)

        setupObservers()
        connectFragmentToViewModel()

        binding.mainToolbarBurger.setOnClickListener {
            activity?.findViewById<DrawerLayout>(R.id.activity_main_layout)?.openDrawer(GravityCompat.START)
        }

//        val textView: TextView? = activity?.findViewById<TextView>(R.id.main_temperature_now_tv)
//        textView?.text = "65"

        return binding.root
    }

    private fun connectFragmentToViewModel() {
        viewModel.backgroundResource.observe(viewLifecycleOwner) {
            binding.mainBackgroundImg.setBackgroundResource(it)
        }
        binding.mainBackgroundImg.setAltImageResource(R.color.black)
    }

    private fun setupObservers() {
        viewModel.getWeatherData().observe(requireActivity()) {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        setMainHeaderValues(binding.fragmentContentMainHeader, resource.data)
                        Toast.makeText(context, "Success", Toast.LENGTH_LONG).show()
                    }
                    Status.ERROR -> {
                        Toast.makeText(context, "Error", Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        Toast.makeText(context, "Loading", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun setMainHeaderValues(layout: ContentMainHeaderBinding, weather: Weather?) {
        layout.mainTemperatureNowTv.text = weather?.currentWeather?.temperature?.let {
            TemperatureModel(it).toString()
        }

        val minTemperature = weather?.dailyWeather?.get(0)?.temperature?.minTemperature?.let {
            TemperatureModel(it)
        }
        val maxTemperature = weather?.dailyWeather?.get(0)?.temperature?.maxTemperature?.let {
            TemperatureModel(it)
        }
        val temperatureTodayText = "$minTemperature / $maxTemperature"
        layout.mainTemperatureTodayTv.text = temperatureTodayText

        val temperatureFeelsLike = weather?.currentWeather?.feelsLike?.let {
            TemperatureModel(it)
        }
        val temperatureFeelsLikeText = "Ощущается как $temperatureFeelsLike"
        layout.mainFeelsLikeTemperatureTv.text = temperatureFeelsLikeText
    }
}