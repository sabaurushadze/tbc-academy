package com.example.academy_tbc

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.AppCompatToggleButton
import androidx.core.os.LocaleListCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (AppCompatDelegate.getApplicationLocales().isEmpty) {
            val defaultLocale = LocaleListCompat.forLanguageTags("ka")
            AppCompatDelegate.setApplicationLocales(defaultLocale)
        }

        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        numberToTextConverter()
    }

    private fun numberToTextConverter() {
        val btnCalculate: AppCompatButton = findViewById(R.id.btnCalculate)
        val etNumber: AppCompatEditText = findViewById(R.id.etNumber)
        val result: AppCompatTextView = findViewById(R.id.tvResult)
        val btnToggleLanguage: AppCompatToggleButton = findViewById(R.id.btnToggleLanguage)



        btnToggleLanguage.setOnClickListener {
            val currentLanguage = AppCompatDelegate.getApplicationLocales()[0]?.language ?: "ka"

            val appLocaleEn: LocaleListCompat = LocaleListCompat.forLanguageTags("en")
            val appLocaleGe: LocaleListCompat = LocaleListCompat.forLanguageTags("ka")

            when (currentLanguage) {
                "ka" -> AppCompatDelegate.setApplicationLocales(appLocaleEn)
                "en" -> AppCompatDelegate.setApplicationLocales(appLocaleGe)
            }
        }


        btnCalculate.setOnClickListener {
            val number = etNumber.text.toString()

            val numberToTextConverterGeorgian = NumberToTextConverter(number, this)
            val numberToTextConverterEnglish = NumberToTextConverterEnglish(number, this)

            val numberToTextGeorgian = numberToTextConverterGeorgian.convertNumberToText()
            val numberToTextEnglish = numberToTextConverterEnglish.convertNumberToText()

            val toasts = listOf("toastStartsZero", "toastNumberRange")

            val getAppLocale = AppCompatDelegate.getApplicationLocales().toLanguageTags()
            when (getAppLocale) {
                "ka" -> result.text =
                    if (numberToTextGeorgian !in toasts) numberToTextGeorgian else ""

                "en" -> result.text =
                    if (numberToTextEnglish !in toasts) numberToTextEnglish else ""

                else -> result.text =
                    if (numberToTextGeorgian !in toasts) numberToTextGeorgian else ""
            }

            when (numberToTextGeorgian) {
                "toastStartsZero" -> Toast.makeText(
                    this,
                    getString(R.string.cant_start_with_zero), Toast.LENGTH_SHORT
                ).show()

                "toastNumberRange" -> Toast.makeText(
                    this,
                    getString(R.string.input_from_one_to_thousand), Toast.LENGTH_SHORT
                ).show()

                else -> ""
            }

        }
    }
}
