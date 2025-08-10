package com.example.footprint

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.footprint.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ViewBinding za activity_main.xml
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Povezivanje Toolbar-a iz layouta
        val toolbar: Toolbar = binding.topAppBar
        setSupportActionBar(toolbar)

        // NavHostFragment za kontrolu navigacije
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        navController = navHostFragment.navController

        // Definisanje top-level destinacija (bez back dugmeta u toolbaru)
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.homeFragment, R.id.cameraFragment, R.id.searchFragment)
        )

        // Toolbar se ponaša u skladu sa navigacijom
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration)

        // Povezivanje bottom navigation sa navController-om
        val bottomNavigationView: BottomNavigationView = binding.bottomNavView
        bottomNavigationView.setupWithNavController(navController)
    }

    // Učitavanje toolbar menija (lupa i zvonce)
    override fun onCreateOptionsMenu(menu: android.view.Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu) // koristi novi meni koji si napravio
        return true
    }

    // Reakcija na klik dugmića u toolbaru (lupa i zvonce)
    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                navController.navigate(R.id.searchFragment)
                true
            }
            R.id.action_notifications -> {
                navController.navigate(R.id.notificationsFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
