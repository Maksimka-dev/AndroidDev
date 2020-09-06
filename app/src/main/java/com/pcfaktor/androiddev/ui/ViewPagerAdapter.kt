package com.pcfaktor.androiddev.ui

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.pcfaktor.androiddev.AppActivity
import com.pcfaktor.androiddev.ERROR_LINK
import com.pcfaktor.androiddev.ui.bookmarks.BookmarksFragment
import com.pcfaktor.androiddev.ui.feed.FeedFragment
import com.pcfaktor.androiddev.ui.full_article.FullArticleFragment


const val FEED_FRAGMENT = 0
const val BOOKMARKS_FRAGMENT = 1
const val ARTICLE_FRAGMENT = 2

class ViewPagerAdapter(activity: AppActivity) : FragmentStateAdapter(activity) {

    var link: String = ERROR_LINK

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            FEED_FRAGMENT -> FeedFragment.newInstance()
            BOOKMARKS_FRAGMENT -> BookmarksFragment.newInstance()
            else -> FullArticleFragment.newInstance(link)
        }
    }
}