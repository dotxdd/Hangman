package com.example.hangmangame

import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.navigation.fragment.findNavController
import com.example.hangmangame.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            toggle=ActionBarDrawerToggle(this@MainActivity, drawerLayout,R.string.open,
            R.string.close)
            drawerLayout.addDrawerListener(toggle)
            toggle.syncState()

            supportActionBar?.setDisplayHomeAsUpEnabled(true)

            navView.setNavigationItemSelectedListener { menuItem ->
                when (menuItem.itemId) {

                    R.id.firstItem -> {
                        val navController = findNavController(R.id.nav_host_fragment_content_main)
                        val currentDestination = navController.currentDestination
                        if (currentDestination?.id != R.id.helpFragment) {
                            navController.navigate(R.id.helpFragment)
                            Toast.makeText(this@MainActivity, "Are you serious? There is no way that you don't know how to play it!", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this@MainActivity, "You're already here!", Toast.LENGTH_SHORT).show()
                        }
                    }
                    R.id.secondItem -> {
                        val navController = findNavController(R.id.nav_host_fragment_content_main)
                        val currentDestination = navController.currentDestination
                        if (currentDestination?.id != R.id.aboutUsFragment) {
                            navController.navigate(R.id.aboutUsFragment)
                            Toast.makeText(this@MainActivity, "Ok, we will introduce ourselves.", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this@MainActivity, "You're already here!", Toast.LENGTH_SHORT).show()
                        }
                    }
                    R.id.thirdItem -> {
                        val navController = findNavController(R.id.nav_host_fragment_content_main)
                        val currentDestination = navController.currentDestination
                        if (currentDestination?.id != R.id.feedbackFragment) {
                            navController.navigate(R.id.feedbackFragment)
                            Toast.makeText(this@MainActivity, "Let us know what you think!", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this@MainActivity, "You're already here!", Toast.LENGTH_SHORT).show()
                        }
                    }

                }
                true
            }
        }
        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}