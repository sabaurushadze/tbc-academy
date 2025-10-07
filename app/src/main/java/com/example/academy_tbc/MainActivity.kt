package com.example.academy_tbc

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.academy_tbc.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setUpBookingApp()
        clickBookNow()
    }

    private fun setUpBookingApp() {
        binding.ivBookingPlace.setImageResource(R.drawable.andes_mountain)
        binding.tvPlaceName.text = getString(R.string.andes_mountain)
        binding.tvContinentOfPlace.text = getString(R.string.south_america)
        binding.tvPrice.text = getString(R.string.price)
        binding.tvCurrency.text = getString(R.string.currency_usd)
        binding.tvPriceAmount.text = getString(R.string.andes_price_amount)
        binding.tvTimeValue.text = getString(R.string.andes_time)
        binding.tvTemperatureValue.text = getString(R.string.temperature_in_andes)
        binding.tvRatingValue.text = getString(R.string.andes_rating)
        binding.tvPlaceDescription.text = getString(R.string.andes_overview)

    }

    private fun clickBookNow() {
        binding.btnBookNow.setOnClickListener {
            Toast.makeText(this, getString(R.string.booking_successful), Toast.LENGTH_SHORT).show()
        }
    }

}