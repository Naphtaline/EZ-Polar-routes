package com.example.ezpolarroutes

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import com.example.ezpolarroutes.databinding.ActivityMapsBinding
import com.example.ezpolarroutes.databinding.CredsDialogBinding

class CredsDialog(context: Context): Dialog(context) {
    private lateinit var binding: CredsDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CredsDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val pref = AppPreferences(context)

        binding.credsButton.setOnClickListener {
            pref.username = binding.credsUserField.text.toString()
            pref.password = binding.credsPassField.text.toString()
            this.dismiss()
        }

        binding.credsUserField.setText(pref.username.toString())
        binding.credsPassField.setText(pref.password.toString())
    }

}