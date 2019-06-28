package com.saumya.pgtask

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class Splash : AppCompatActivity() {

    private var handler : Handler? =null
    private val SplashLength:Long=2000
    private val handlerRunnable= Runnable {

        if ( !isFinishing){
            startActivity(Intent(baseContext,MainActivity::class.java))
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val handler=Handler()
        handler!!.postDelayed(handlerRunnable , SplashLength)
    }

    override fun onDestroy() {
        if (handler!=null){
            handler!!.removeCallbacks(handlerRunnable)
        }
        super.onDestroy()
    }
}
