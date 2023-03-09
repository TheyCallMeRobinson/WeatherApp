package cs.vsu.ru.application.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import cs.vsu.ru.application.R
import cs.vsu.ru.application.databinding.ContentMainBodyListBinding
import cs.vsu.ru.application.databinding.ContentMainHeaderBinding
import cs.vsu.ru.application.databinding.FragmentContentMainBinding
import cs.vsu.ru.application.model.TemperatureModel
import cs.vsu.ru.application.view.adapter.HourlyListAdapter
import cs.vsu.ru.application.viewmodel.MainViewModel
import cs.vsu.ru.domain.model.weather.Weather
import cs.vsu.ru.environment.Status
import org.koin.androidx.viewmodel.ext.android.viewModel


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
                        setMainBodyListValues(binding.mainBodyList, resource.data)
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

    private fun setMainBodyListValues(layout: ContentMainBodyListBinding, weather: Weather?) {
        val hourlyListAdapter = weather?.hourlyWeather?.let { HourlyListAdapter(it, weather.timezoneOffset) }
        val hourlyList = layout.hourlyTemperatureListContainer.hourlyTemperatureList
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        hourlyList.layoutManager = linearLayoutManager
        hourlyList.adapter = hourlyListAdapter
        hourlyList.setHasFixedSize(true)
    }
}