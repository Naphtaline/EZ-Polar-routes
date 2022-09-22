package com.rcorp.polarroute.data.remote

import com.rcorp.polarroute.model.GPSData
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.*

interface ApiDescription {
    @POST("/login")
    @FormUrlEncoded
    @Headers("Host: flow.polar.com",
        "Sec-Ch-Ua: \"Chromium\";v=\"105\", \"Not)A;Brand\";v=\"8\"",
        "Accept: application/json, text/javascript, */*; q=0.01",
        "X-Requested-With: XMLHttpRequest",
        "Sec-Ch-Ua-Mobile: ?0",
        "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/105.0.5195.102 Safari/537.36",
        "Sec-Ch-Ua-Platform: \"Linux\"",
        "Origin: https://flow.polar.com",
        "Sec-Fetch-Site: same-origin",
        "Sec-Fetch-Mode: cors",
        "Sec-Fetch-Dest: empty",
        "Accept-Encoding: gzip, deflate",
        "Accept-Language: en-GB,en-US;q=0.9,en;q=0.8}"
    )
    fun login(@Field("email")email: String?, @Field("password")password: String?): Single<Response<String>>
    @POST("/api/favorites/trainingTargets/importRoute")
    @Headers("Host: flow.polar.com",
        "Sec-Ch-Ua: \"Chromium\";v=\"105\", \"Not)A;Brand\";v=\"8\"",
        "Accept: application/json, text/javascript, */*; q=0.01",
        "X-Requested-With: XMLHttpRequest",
        "Sec-Ch-Ua-Mobile: ?0",
        "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/105.0.5195.102 Safari/537.36",
        "Sec-Ch-Ua-Platform: \"Linux\"",
        "Origin: https://flow.polar.com",
        "Sec-Fetch-Site: same-origin",
        "Sec-Fetch-Mode: cors",
        "Sec-Fetch-Dest: empty",
        "Accept-Encoding: gzip, deflate",
        "Accept-Language: en-GB,en-US;q=0.9,en;q=0.8}"
    )
    fun upload_data(@Body()value: GPSData): Single<Response<String>>
}