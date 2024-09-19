package com.example.my_app

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import com.example.my_app.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.time.Duration

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .setAnchorView(R.id.fab).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        println("HERE")
        someFuncInLibraryThatDoesHTTPRequestApp()
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


     fun someFuncInLibraryThatDoesHTTPRequestApp() {
        try {
            // Inside your activity or fragment
            CoroutineScope(Dispatchers.IO).launch {
                var client = OkHttpClient()
                val clientBuilder = client.newBuilder()
                clientBuilder.callTimeout(Duration.ofHours(1))
                client = clientBuilder.build()

                val request = Request.Builder().url("https://example.com").get().build()

                try {
                    val response: Response = client.newCall(request).execute()
                    if (response.isSuccessful) {
                        val responseData = response.body?.string()  // Read the response body
                        println("~~~~~~~~Response Data: $responseData")
                    } else {
                        println("~~~~~~~~Request failed with status code: ${response.code}")
                    }
                } catch (e: Exception) {
                    println("~~~~~~~~Oho, exception:" + e.toString())
                }
            }
        }catch (e: Exception) {
            println("~~~~~~~~Oho, exception in coroutine:" + e.toString())
        }


    }
}