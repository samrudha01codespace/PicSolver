package com.example.picsolver

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import io.github.inflationx.viewpump.ViewPump
import io.github.inflationx.viewpump.ViewPumpContextWrapper.Companion.wrap

@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {

    private var getStarted: AppCompatButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        getStarted = findViewById(R.id.getStartedButton)

        getStarted!!.setOnClickListener {
            startActivity(Intent(applicationContext, MainActivity::class.java))
        }
    }

    override fun attachBaseContext(newBase: Context?) {
        val viewPump = ViewPump.builder().build()
        super.attachBaseContext(
            wrap(newBase!!, viewPump)
        )
    }

}
