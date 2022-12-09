package com.suleymanbilgin.starterf

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    val pm = "com.turkishairlines.mobile.preprod"
    val link = "https://www.turkishairlines.com/star-biometrics"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnOpen: Button = findViewById(R.id.btn_open)
        btnOpen.setOnClickListener {
            startApp(pm, link)
        }
    }

    fun startApp(packageName: String, schema: String) {
        val schemaIntent = Intent(Intent.ACTION_VIEW, Uri.parse(schema))
        // trying to open the app using schema
        if (schemaIntent.resolveActivity(packageManager) != null) {
            startActivity(schemaIntent)
        } else {
            // if the schema is not supported, open Google Play to download the app
            val intent = packageManager.getLaunchIntentForPackage(packageName)
            if (intent == null) {
                try {
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("market://details?id=$packageName")
                        )
                    )
                } catch (e: ActivityNotFoundException) {
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
                        )
                    )
                }
            } else {
                // the schema is not supported, but the app is installed (probably the airline app needs to be updated.
                startActivity(intent)
            }
        }
    }
}