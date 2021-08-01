package com.mityushov.mapapplication

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.mityushov.mapapplication.databinding.ActivityGeoBinding

private const val TAG: String = "MainActivity"

class GeoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGeoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate() method")
        binding = ActivityGeoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.idBShowMap.setOnClickListener {
            val address = binding.idEtAddressField.text.toString().apply {
                replace(" ", "+")
            }
            val geoIntent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=$address"))
            startActivity(geoIntent)
        }

    }
}