package com.pcfaktor.androiddev

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pcfaktor.androiddev.ui.feed.FeedFragment

class AppActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, FeedFragment.newInstance())
                .commitNow()
        }
    }
}