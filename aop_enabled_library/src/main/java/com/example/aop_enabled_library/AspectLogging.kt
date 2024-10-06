package com.example.aop_enabled_library

import android.util.Log
import okhttp3.Authenticator
import okhttp3.Credentials
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.Route
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import java.lang.Exception
import java.net.InetSocketAddress
import java.net.Proxy

@Aspect
class OkHttpProxyAspect {

    @Before("call(* okhttp3.OkHttpClient.Builder.build(..))")
    fun interceptOkHttpClientBuild(joinPoint: JoinPoint) {
        try {
            Log.i("aop_enabled_library", "Called function interceptOkHttpClientBuild")
            val builder = joinPoint.target as OkHttpClient.Builder

            // Define proxy settings (modify as needed)
            val proxy = Proxy(Proxy.Type.HTTP, InetSocketAddress("127.0.0.1", 8080))

            val authenticator = Authenticator { _: Route?, response: Response ->
                val credential = Credentials.basic("username", "password")
                response.request.newBuilder()
                    .header("Proxy-Authorization", credential)
                    .build()
            }
            builder.proxyAuthenticator(authenticator)

            // Modify the builder to add proxy before building OkHttpClient
            builder.proxy(proxy)

            Log.i("aop_enabled_library", "Proxy added to OkHttpClient.Builder")
        }catch (e : Exception) {
            Log.e("aop_enabled_library", "CRASHHHH $e")
        }

    }
}