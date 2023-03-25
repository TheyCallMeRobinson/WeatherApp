package cs.vsu.ru.application.view

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionScene
import cs.vsu.ru.application.R
import cs.vsu.ru.application.databinding.ActivityMainBinding
import cs.vsu.ru.application.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var motion: MotionScene

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        val actionBarDrawerToggle = object : ActionBarDrawerToggle(this, binding.root,
            R.string.drawer_open,
            R.string.drawer_close
        ) {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                binding.mainFragmentContentContainer.translationX = binding.mainFragmentDrawerContainer.width * slideOffset
                super.onDrawerSlide(drawerView, slideOffset)
            }
        }

//        binding.root.setScrimColor(resources.getColor(android.R.color.transparent))

        binding.root.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        setViews()
    }

    private fun setViews() {

    }

}