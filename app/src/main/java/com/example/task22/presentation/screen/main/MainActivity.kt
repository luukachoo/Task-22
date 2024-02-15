package com.example.task22.presentation.screen.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.task22.R
import com.example.task22.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        setUpNavGraph()
        handleNavigationBar()
    }

    private fun handleNavigationBar() = with(binding) {
        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.homeIcon -> navController.navigate(R.id.homeFragment)

                R.id.chatIcon -> navController.navigate(R.id.commentsFragment)

                R.id.heartIcon -> navController.navigate(R.id.favouritesFragment)

                R.id.bellIcon -> navController.navigate(R.id.notificationsFragment)
            }
            true
        }
    }

    private fun setUpNavGraph() {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.findNavController()
        binding.bottomNavigationView.setupWithNavController(navController)
    }
}