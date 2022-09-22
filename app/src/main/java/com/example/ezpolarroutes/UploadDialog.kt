package com.example.ezpolarroutes

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import com.example.ezpolarroutes.databinding.ActivityMapsBinding
import com.example.ezpolarroutes.databinding.CredsDialogBinding
import com.example.ezpolarroutes.databinding.UploadDialogBinding

class UploadDialog(val activity: MapsActivity): Dialog(activity) {
    private lateinit var binding: UploadDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = UploadDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.uploadGpsDialogButton.setOnClickListener {
            if (binding.uploadGpsDialogName.text.toString() != ""){
                activity.uploadTheCurrentGPSTrace(binding.uploadGpsDialogName.text.toString())
                this.dismiss()
            }
        }
    }
}