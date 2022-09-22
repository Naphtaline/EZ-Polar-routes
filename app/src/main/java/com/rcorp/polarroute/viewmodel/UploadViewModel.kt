package com.rcorp.polarroute.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rcorp.polarroute.data.remote.WebClient
import com.rcorp.polarroute.model.GPSData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.java.KoinJavaComponent.get

class UploadViewModel() : ViewModel() {

    private val webClient = get<WebClient>(WebClient::class.java)

    val progress = MutableLiveData(false)
    val status = MutableLiveData<Status?>(null)

    fun uploadData(data: GPSData) {
        progress.value = true
        webClient.upload(data)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                progress.value = false
                status.value = Status.SUCCESS
            }, {
                progress.value = false
                status.value = Status.FAIL
                Log.e("Error", "Unable to perform upload", it)
            })
    }

    enum class Status {
        SUCCESS,
        FAIL
    }
}