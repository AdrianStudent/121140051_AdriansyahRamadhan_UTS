package com.example.utspam

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        // Handler untuk menunggu 3 detik sebelum beralih ke RegisterActivity
        Handler().postDelayed({
            // Intent untuk beralih ke RegisterActivity
            startActivity(Intent(this@SplashScreen, RegisterActivity::class.java))
            finish() // Menutup SplashScreen agar tidak kembali lagi saat tombol back ditekan
        }, 3000) // Delay 3 detik
    }
}
