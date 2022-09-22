package com.rcorp.polarroute.data.remote

import android.util.Log
import com.google.gson.Gson
import com.rcorp.polarroute.data.local.AppPreferences
import com.rcorp.polarroute.model.GPSData
import io.reactivex.rxjava3.core.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class WebClient(private val appPreferences: AppPreferences) {

    private val apiClient: ApiDescription
    private var token = appPreferences.token ?: ""

    init {
        val clientHttp = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor {
                var request = it.request()
                request = request.newBuilder().addHeader(
                    AUTHORIZATION_KEY,
                    token
                ).build()
                it.proceed(request)
            }
            .addInterceptor { chain ->
                val response = chain.proceed(chain.request())
                if ((response.code() == 401 || response.code() == 403) && chain.request().url().pathSegments().last() != TOKEN_REQUEST) {
                    try {
                        response.close()
                            login(appPreferences.username, appPreferences.password).blockingGet()
                    } catch (e: Exception) {
                        Log.e("Error", "Unable to login", e)
                    }

                    val request = chain.request().newBuilder()
                        .removeHeader(AUTHORIZATION_KEY)
                        .addHeader(AUTHORIZATION_KEY, token)
                        .build()
                    chain.proceed(request)
                } else
                    response

            }
            .build()

        val retroFit = Retrofit.Builder().client(clientHttp)
            .baseUrl("https://flow.polar.com")
            .addConverterFactory(
                GsonConverterFactory.create(
                    Gson().newBuilder().setLenient().create()
                )
            )
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()

        apiClient = retroFit.create(ApiDescription::class.java)
    }

    fun login(user: String?, pass: String?): Single<Boolean> {
        return apiClient.login(user, pass)
            .map { response ->
                var setCookie = ""
                response.headers().toMultimap().entries.forEach {
                    if (it.key == "set-cookie")
                        setCookie += it.value
                }
                token = ""
                setCookie.split(";").forEach { cookieValue ->
                    if (cookieValue.contains("timezone")
                        || cookieValue.contains("POLAR")
                        || cookieValue.contains("FLOW")
                        || cookieValue.contains("AWS")
                        || cookieValue.contains("Optanon")
                    ) {
                        val array = cookieValue.split(" ")
                        token += if (array.size > 2) {
                            array[2] + "; "
                        } else {
                            "$cookieValue; "
                        }
                    }
                }
                token = token.removePrefix("[")
                appPreferences.token = token
                true
            }
    }

    fun upload(data: GPSData): Single<Boolean> {
        return apiClient.upload_data(data)
            .map { true }
    }

    companion object {
        private const val TOKEN_REQUEST = "login"
        private const val AUTHORIZATION_KEY = "Cookie"
    }
}