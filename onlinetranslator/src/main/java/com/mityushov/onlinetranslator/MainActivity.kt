package com.mityushov.onlinetranslator

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.mityushov.onlinetranslator.databinding.ActivityMainBinding
import org.jsoup.Jsoup
import org.jsoup.nodes.Element


private const val TAG = "MainActivity"

private val languages = listOf("arabic", "german", "english",
                                "spanish", "french", "hebrew",
                                "italian", "japanese", "polish",
                                "portuguese", "romanian", "russian",
                                "turkish", "chinese")

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, languages)
        binding.spinnerFromLang.adapter = adapter
        binding.spinnerToLang.adapter = adapter
        // default language
        var fromLang = "english"
        var toLang = "russian"

        binding.spinnerFromLang.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long,
            ) {
                Log.d(TAG, "spinnerFromLang.onItemSelectedListener.onItemSelected() is called")
                if (view != null) {
                    fromLang = (view as TextView).text.toString()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.d(TAG, "spinnerFromLang.onItemSelectedListener.onNothingSelected() is called")
            }
        }

        binding.spinnerToLang.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long,
            ) {
                Log.d(TAG, "spinnerToLang.onItemSelectedListener.onItemSelected() is called")

                if (view != null ) {
                    toLang = (view as TextView).text.toString()
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.d(TAG, "spinnerToLang.onItemSelectedListener.onNothingSelected() is called")
            }
        }

        binding.translateButton.setOnClickListener {
            binding.tvResult.text = ""
            // start routine in another thread
            Thread {
                val url = "https://context.reverso.net/translation/${fromLang}-${toLang}/${binding.etInputText
                    .text
                    .toString()
                    .replace(" ", "+")}"

                Log.d(TAG, "onClick(), url = $url")

                val doc = Jsoup.connect(url).get()
                val element: Element? = doc.getElementById("translations-content")
                Log.d(TAG, "translation content is: ${element?.text()}")
                this.runOnUiThread {
                    binding.tvResult.text = element?.text()
                }
            }.start()

            val handler = Handler()
        }
    }

}