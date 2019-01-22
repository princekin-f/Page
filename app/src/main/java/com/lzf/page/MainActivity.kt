package com.lzf.page

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pageView: PageView = findViewById(R.id.page)
        val button: Button = findViewById(R.id.button)

        button.setOnClickListener {
            pageView.startAnimation()
        }

    }

}
