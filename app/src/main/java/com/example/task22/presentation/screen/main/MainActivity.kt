package com.example.task22.presentation.screen.main

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.task22.R
import com.example.task22.databinding.ActivityMainBinding
import com.google.android.datatransport.runtime.logging.Logging.d
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private var request =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermission()
        setContentView(binding.root)
        init()
    }

    private fun init() {
        setUpNavGraph()
        handleNavigationBar()
        getToken()
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

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            request.launch(arrayOf(Manifest.permission.POST_NOTIFICATIONS))
        }
    }

    private fun getToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                d("TAG", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            d("TAG_TOKENNN", token)
        })
    }
}