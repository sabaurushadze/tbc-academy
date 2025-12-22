package com.example.academy_tbc

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.academy_tbc.databinding.ActivityMainBinding
import com.example.academy_tbc.presentation.extension.visibleIf
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initBottomNavigation()
    }

    private fun initBottomNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        navController.graph = navController.navInflater.inflate(R.navigation.nav_graph)

        binding.bottomNavigationView.setupWithNavController(navController)
        binding.bottomNavigationView.setOnApplyWindowInsetsListener(null)

        binding.bottomNavigationView.setOnItemReselectedListener { }

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            navController.navigate(
                item.itemId,
                null,
                NavOptions.Builder()
                    .setLaunchSingleTop(true)
                    .setPopUpTo(
                        R.id.homeFragment,
                        inclusive = false,
                        saveState = false
                    )
                    .setRestoreState(true)
                    .build()
            )
            true
        }
        setBottomNavBarVisibility()
    }

    private fun getVisibleNavFragmentIds(): List<Int> {
        return listOf(
            R.id.homeFragment,
            R.id.profileFragment,
            R.id.myEventsFragment,
            R.id.eventsFragment,
            R.id.notificationsFragment,
            R.id.eventDetailsFragment
        )
    }

    private fun setBottomNavBarVisibility() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.bottomNavigationView.visibleIf(
                destination.id in getVisibleNavFragmentIds()
            )
        }
    }


}