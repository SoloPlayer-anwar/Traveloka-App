package com.example.pullcode.ui.dashboard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.example.pullcode.R
import com.example.pullcode.databinding.ActivityBottomNavigationBinding
import com.example.pullcode.ui.dashboard.explore.ExploreFragment
import com.example.pullcode.ui.dashboard.home.HomeFragment
import com.example.pullcode.ui.dashboard.profile.ProfileFragment
import com.example.pullcode.ui.dashboard.swab.SwabFragment

class BottomNavigationActivity : AppCompatActivity() {
    private lateinit var bottomBinding: ActivityBottomNavigationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bottomBinding = ActivityBottomNavigationBinding.inflate(layoutInflater)
        setContentView(bottomBinding.root)

        replaceFragment(HomeFragment.newInstance())
        bottomBinding.bottomNavigation.add(MeowBottomNavigation.Model(0,R.drawable.ic_nav_home))
        bottomBinding.bottomNavigation.add(MeowBottomNavigation.Model(1,R.drawable.ic_nav_explore))
        bottomBinding.bottomNavigation.add(MeowBottomNavigation.Model(2,R.drawable.ic_nav_ticket))
        bottomBinding.bottomNavigation.add(MeowBottomNavigation.Model(3,R.drawable.ic_nav_profile))

        bottomBinding.bottomNavigation.setOnClickMenuListener {
            when(it.id) {
                0 -> {
                    replaceFragment(HomeFragment.newInstance())
                }

                1 -> {
                    replaceFragment(ExploreFragment.newInstance())
                }

                2 -> {
                    replaceFragment(SwabFragment.newInstance())
                }


                3 -> {
                    replaceFragment(ProfileFragment.newInstance())
                }
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.replace(R.id.fragmentContainer,fragment)
        fragmentTransition.commit()
    }

}