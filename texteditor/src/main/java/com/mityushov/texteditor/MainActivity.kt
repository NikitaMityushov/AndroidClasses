package com.mityushov.texteditor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import com.mityushov.texteditor.databinding.ActivityMainBinding

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "MainActivity.onCreate() is called")
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

// 1) Init local storage :)))
        var buffer = ""
// 2) Button listeners
        binding.saveButton.setOnClickListener {
            Log.d(TAG, "Save Button is clicked")
            binding.unsavedChangesView.setText(R.string.all_changes_saved)
            buffer = binding.editText.text.toString()
        }

        binding.loadButton.setOnClickListener {
            Log.d(TAG, "Load Button is clicked")
            binding.editText.setText(buffer)
        }

        binding.clearButton.setOnClickListener {
            Log.d(TAG, "Clear Button is clicked")
            binding.editText.setText("")
            binding.unsavedChangesView.setText((R.string.unsaved_changes))
        }
// 3) EditText events
        binding.editText.addTextChangedListener(object : TextWatcher {
            val regex: Regex = Regex("""[^A-Za-zА-Яа-я0-9_]""")

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                Log.d(TAG, "beforeTextChanged() method called")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.d(TAG, "onTextChanged() method called")
            }

            override fun afterTextChanged(s: Editable?) {
                Log.d(TAG, "afterTextChanged() method called")
                val size = regex.split(s?.toString()?: "").filter { it != "" }.size
                binding.statsView.text = size.toString()
                binding.unsavedChangesView.setText((R.string.unsaved_changes))
            }

        })
    }
}