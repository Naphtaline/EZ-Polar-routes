package com.example.ezpolarroutes

import android.R.attr.port
import android.util.Log
import com.google.gson.Gson
import io.reactivex.rxjava3.core.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.net.InetSocketAddress
import java.net.Proxy


class WebClient {
    val maPetiteApi: ApiDescription
    var new_cookie = ""

    init {
        val clientHttp = OkHttpClient.Builder()
            .addInterceptor(okhttp3.logging.HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

        val retroFit = Retrofit.Builder().client(clientHttp)
            .baseUrl("https://flow.polar.com")
            .addConverterFactory(GsonConverterFactory.create(Gson().newBuilder().setLenient().create()))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()

        maPetiteApi = retroFit.create(ApiDescription::class.java)
    }

    fun login(user:String?, pass:String?): Single<Boolean> {
        return maPetiteApi.login(user, pass)
            .map {
                var setCookie = ""
                it.headers().toMultimap().entries.forEach({
                    if (it.key == "set-cookie")
                        setCookie += it.value
                })
                new_cookie = ""
                setCookie.split(";").forEach {
                    if (it.contains("timezone")
                        || it.contains("POLAR")
                        || it.contains("FLOW")
                        || it.contains("AWS")
                        || it.contains("Optanon")) {
                        var array = it.split(" ")
                        if (array.size > 2) {
                            new_cookie += array[2] + "; "
                        }
                        else {
                            new_cookie += it + "; "
                        }
                    }
                }
                new_cookie = new_cookie.removePrefix("[")
                Log.i("toto", new_cookie)
                true
            }
    }

    fun  upload(data: GPSData): Single<Boolean>  {
        return maPetiteApi.upload_data(data, headers = new_cookie)
            .map { true }
    }
}