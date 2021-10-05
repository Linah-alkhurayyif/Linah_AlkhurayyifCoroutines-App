package com.example.linah_alkhurayyifcoroutines_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import kotlinx.coroutines.*
import org.json.JSONObject
import java.net.URL

class MainActivity : AppCompatActivity() {
    val adviceURL = "https://api.adviceslip.com/advice"
    lateinit var adviceText:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
         adviceText = findViewById<TextView>(R.id.adviceText)
        val adviceButton = findViewById<Button>(R.id.advice)
        adviceButton.setOnClickListener(){

                api_request()
        }
    }
    private fun api_request()
    {
            CoroutineScope(Dispatchers.IO).launch {
                val data = async {
                    fetch_Advice()
                }.await()
                if (data.isNotEmpty())
                {


                        getResult(data)

                }
            }

    }
    private fun fetch_Advice():String{
        var url_response = ""
        try {
            url_response = URL(adviceURL).readText(Charsets.UTF_8)

        }catch (e:Exception)
        {
            println("Something wrong: $e")
        }
        return url_response
    }
    private suspend fun getResult(data:String)
    {

            withContext(Dispatchers.Main)
            {

                val jsonObject = JSONObject(data)
                val slip = jsonObject.getJSONObject("slip")
                val id = slip.getInt("id")
                val advice = slip.getString("advice")

                adviceText.text = advice

            }



    }
}