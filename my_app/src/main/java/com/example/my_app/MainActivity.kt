package com.example.my_app

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.time.Duration

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.home_page)

        val button: Button = findViewById(R.id.button)

        button.setOnClickListener {
            someFuncInApplicationThatDoesHTTPRequest()
        }
    }

     private fun someFuncInApplicationThatDoesHTTPRequest() {
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
                        Log.i("my_app", "~~~~~~~~Response Data: $responseData")
                    } else {
                        Log.i("my_app", "~~~~~~~~Request failed with status code: ${response.code}")
                    }
                } catch (e: Exception) {
                    Log.e("my_app", "~~~~~~~~exception while making request:$e")
                }
            }
        }catch (e: Exception) {
            Log.e("my_app", "~~~~~~~~exception in coroutine:$e")
        }
    }
}