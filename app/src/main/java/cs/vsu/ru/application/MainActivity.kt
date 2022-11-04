package cs.vsu.ru.application

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout.DrawerListener
import cs.vsu.ru.application.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        val actionBarDrawerToggle = object : ActionBarDrawerToggle(this, binding.root, R.string.drawer_open, R.string.drawer_close) {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                binding.mainFragmentContentContainer.translationX = binding.mainFragmentDrawerContainer.width * slideOffset
                super.onDrawerSlide(drawerView, slideOffset)
            }
        }

        binding.root.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        setContentView(binding.root)
        setViews()

//
//        binding.mainBackgroundImg.setBackgroundResource(R.drawable.background_daytime)
//        binding.mainBackgroundImg.setAltImageResource(R.color.black)
    }

    private fun setViews() {

    }

}