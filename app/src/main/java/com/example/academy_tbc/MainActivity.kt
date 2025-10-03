package com.example.academy_tbc

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.academy_tbc.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var anagramMap: MutableMap<String, MutableSet<String>> = mutableMapOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        save()
        output()
        clear()
    }

    private fun save() {
        binding.btnSave.setOnClickListener {
            val formattedInputText = formatInputText(binding.etAnagram.text.toString())
            val originalInputText = binding.etAnagram.text.toString()

            if (binding.etAnagram.text!!.isEmpty()) {
                binding.etAnagram.error = getString(R.string.enter_an_anagram)
                return@setOnClickListener
            }

            if (!anagramMap.containsKey(formattedInputText)) {
                anagramMap[formattedInputText] = mutableSetOf()
            }
            anagramMap[formattedInputText]?.add(originalInputText)
            binding.etAnagram.text?.clear()

        }
    }

    private fun output() {
        binding.btnOutput.setOnClickListener {
            binding.tvResult.text = getString(R.string.number_of_anagram_groups, anagramMap.size)
        }
    }

    private fun clear() {
        binding.btnClear.setOnClickListener {
            binding.etAnagram.text?.clear()
            binding.tvResult.text = ""
            anagramMap.clear()
        }
    }

    private fun formatInputText(inputText: String): String {
        return inputText.sortedInput().lowercase()
    }
}