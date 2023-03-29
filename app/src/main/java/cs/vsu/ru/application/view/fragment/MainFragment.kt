package cs.vsu.ru.application.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableRow
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionInflater
import cs.vsu.ru.application.R
import cs.vsu.ru.application.databinding.ContentMainBodyListBinding
import cs.vsu.ru.application.databinding.ContentMainHeaderBinding
import cs.vsu.ru.application.databinding.FragmentContentMainBinding
import cs.vsu.ru.application.databinding.ItemDailyWeatherBinding
import cs.vsu.ru.application.model.*
import cs.vsu.ru.application.view.adapter.HourlyListAdapter
import cs.vsu.ru.application.view.adapter.HourlyTemperatureGraphCurvyItemDecorator
import cs.vsu.ru.application.viewmodel.MainViewModel
import cs.vsu.ru.environment.Status
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class MainFragment : Fragment() {

    private lateinit var binding: FragmentContentMainBinding
    private val viewModel by activityViewModel<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContentMainBinding.inflate(inflater)

        binding.fragmentContentMainProgressBar.visibility = View.VISIBLE

        val transitionInflater = TransitionInflater.from(requireContext())
        enterTransition = transitionInflater.inflateTransition(R.transition.slide_to_bottom)
        exitTransition = transitionInflater.inflateTransition(R.transition.slide_to_top)

        viewModel.refreshData()
        setupObservers()

        binding.toFragmentMainBtn.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_mainFragment_to_routeFragment)
        )

        binding.mainToolbarBurger.setOnClickListener {
            activity?.findViewById<DrawerLayout>(R.id.activity_main_layout)?.openDrawer(GravityCompat.START)
        }

        return binding.root
    }

    private fun setupObservers() {
        viewModel.backgroundResourceLiveData.observe(viewLifecycleOwner) {
            binding.mainStartBackgroundImg.setBackgroundResource(it)
            binding.mainEndBackgroundImg.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.dark_blue))
        }
        viewModel.weatherLiveData.observe(viewLifecycleOwner) {
            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        binding.fragmentContentMainProgressBar.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        binding.fragmentContentMainProgressBar.visibility = View.INVISIBLE
                        setMainHeaderValues(binding.fragmentContentMainHeader, resource.data)
                        setMainBodyListValues(binding.mainBodyList, resource.data)
                    }
                    Status.ERROR -> {
                        Toast.makeText(context, resource.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun setMainHeaderValues(layout: ContentMainHeaderBinding, weather: WeatherUIModel?) {
        layout.mainLocationNameTv.text = weather?.currentWeather?.location
        layout.currentWeatherIcon.setImageBitmap(weather?.currentWeather?.icon)
        layout.mainDaytimeTv.text = weather?.currentWeather?.localDateTime
        layout.mainLocationToolbarNameTv.text = weather?.currentWeather?.location
        layout.mainTemperatureNowTv.text = weather?.currentWeather?.currentTemperature
        layout.mainTemperatureTodayTv.text = weather?.currentWeather?.dayNightTemperature
        layout.mainFeelsLikeTemperatureTv.text = weather?.currentWeather?.feelsLikeTemperature
    }

    private fun setMainBodyListValues(layout: ContentMainBodyListBinding, weather: WeatherUIModel?) {
        setHourlyTemperatureList(layout, weather)
        setDailyWeatherList(weather?.dailyWeather!!)
        setSunsetSunriseTime(layout, weather.currentWeather)
        setAdditionalWeatherData(layout, weather.currentWeather)
        setApiRefreshTime(layout, weather.apiCallTime)
    }

    private fun setHourlyTemperatureList(layout: ContentMainBodyListBinding, weather: WeatherUIModel?) {
        val hourlyListAdapter = weather?.hourlyWeather?.let { hourlyWeatherList ->
            HourlyListAdapter(hourlyWeatherList, weather.hourlyWeather.map {it.icon})
        }
        val hourlyList: RecyclerView = layout.hourlyTemperatureListContainer.hourlyTemperatureList
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        hourlyList.apply {
            if (hourlyList.itemDecorationCount > 0) {
                removeItemDecorationAt(0)
            }
            addItemDecoration(HourlyTemperatureGraphCurvyItemDecorator(weather!!.hourlyWeather, requireContext()))
            layoutManager = linearLayoutManager
            adapter = hourlyListAdapter
            setHasFixedSize(true)
        }
    }

    private fun setDailyWeatherList(dailyWeatherList: List<DailyWeather>) {
        val dailyWeatherTable = binding.mainBodyList.dailyWeatherListContainer.dailyWeatherList
        dailyWeatherTable.removeAllViews()

        for (item in dailyWeatherList) {
            val tableRow = TableRow(context)
            val tableRowViewBinding = ItemDailyWeatherBinding.inflate(layoutInflater)

            tableRowViewBinding.dayOfWeek.text = item.dayOfWeek
            tableRowViewBinding.itemWeatherIcon.setImageBitmap(item.icon)
            tableRowViewBinding.humidityHolder.itemHumidity.text = item.humidity
            tableRowViewBinding.itemDayTemperature.text = item.dayTemperature
            tableRowViewBinding.itemNightTemperature.text = item.nightTemperature

            tableRow.addView(tableRowViewBinding.root)
            dailyWeatherTable.addView(tableRow)
        }
    }

    private fun setSunsetSunriseTime(layout: ContentMainBodyListBinding, currentWeather: CurrentWeather) {
        layout.sunriseSunsetHolderContainer.sunriseTimeTv.text = currentWeather.sunrise
        layout.sunriseSunsetHolderContainer.sunsetTimeTv.text = currentWeather.sunset
    }

    private fun setAdditionalWeatherData(layout: ContentMainBodyListBinding, currentWeather: CurrentWeather) {
        layout.additionalWeatherInfoHolderContainer.uvIndexValueTv.text = currentWeather.uvIndex
        layout.additionalWeatherInfoHolderContainer.humidityValueTv.text = currentWeather.humidity
        layout.additionalWeatherInfoHolderContainer.windValueTv.text = currentWeather.windSpeed
    }

    private fun setApiRefreshTime(layout: ContentMainBodyListBinding, apiCallTime: String) {
        layout.weatherApiDataHolder.weatherApiRefreshTimeTv.text = apiCallTime
    }
}