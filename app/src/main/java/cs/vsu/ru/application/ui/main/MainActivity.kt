package cs.vsu.ru.application.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import cs.vsu.ru.application.R
import cs.vsu.ru.application.WeatherApplication
import cs.vsu.ru.application.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        setContentView(binding.root)

        setViews()


//        binding.contentMainInc.activityMainHeader.toolbarBurger.setOnClickListener {
//            binding.root.openDrawer(GravityCompat.START)
//        }

        binding.mainBackgroundImg.setBackgroundResource(R.drawable.background_daytime)
        binding.mainBackgroundImg.setAltImageResource(R.color.black)
    }

    private fun setViews() {

    }

}