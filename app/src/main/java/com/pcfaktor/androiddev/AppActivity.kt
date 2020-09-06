package com.pcfaktor.androiddev

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.pcfaktor.androiddev.data.network.ApiService.BASE_URL
import com.pcfaktor.androiddev.databinding.ActivityAppBinding
import com.pcfaktor.androiddev.ui.ARTICLE_FRAGMENT
import com.pcfaktor.androiddev.ui.BOOKMARKS_FRAGMENT
import com.pcfaktor.androiddev.ui.FEED_FRAGMENT
import com.pcfaktor.androiddev.ui.ViewPagerAdapter
import com.pcfaktor.androiddev.ui.full_article.KEY_LINK


const val PAGE_FEED = "feed"
const val PAGE_BOOKMARKS = "bookmarks"
const val PAGE_ARTICLE = "article"
const val ERROR_LINK = "${BASE_URL}en/404"

class AppActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAppBinding
    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: ViewPagerAdapter
    private lateinit var receiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        viewPager = binding.viewpager
        adapter = ViewPagerAdapter(this)
        binding.viewpager.adapter = adapter
        receiveViewPagerCalls()
        setBottomNavigationBar()
    }

    private fun setBottomNavigationBar() {
        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_item_feed -> viewPager.currentItem = FEED_FRAGMENT
                R.id.action_item_bookmarks -> viewPager.currentItem = BOOKMARKS_FRAGMENT
                R.id.action_item_article -> viewPager.currentItem = ARTICLE_FRAGMENT
            }
            true
        }

    }

    private fun receiveViewPagerCalls() {
        val intentFilter = IntentFilter().also {
            it.addAction(PAGE_FEED)
            it.addAction(PAGE_BOOKMARKS)
            it.addAction(PAGE_ARTICLE)
        }
        receiver = object : BroadcastReceiver() {

            override fun onReceive(context: Context?, intent: Intent?) {
                when (intent?.action) {
                    PAGE_FEED -> {
                        viewPager.currentItem = FEED_FRAGMENT
                        binding.bottomNavigation.selectedItemId = R.id.action_item_feed
                    }
                    PAGE_BOOKMARKS -> {
                        viewPager.currentItem = BOOKMARKS_FRAGMENT
                        binding.bottomNavigation.selectedItemId = R.id.action_item_bookmarks
                    }
                    PAGE_ARTICLE -> {
                        adapter.link =
                            intent.extras?.getString(KEY_LINK) ?: ERROR_LINK
                        viewPager.currentItem = ARTICLE_FRAGMENT
                        binding.bottomNavigation.selectedItemId = R.id.action_item_article
                    }
                }
            }
        }
        registerReceiver(receiver, intentFilter)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }
}