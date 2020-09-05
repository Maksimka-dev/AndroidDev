package com.pcfaktor.androiddev.ui.full_article

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.webkit.WebSettingsCompat
import androidx.webkit.WebViewFeature
import com.pcfaktor.androiddev.ERROR_LINK
import com.pcfaktor.androiddev.PAGE_ARTICLE
import com.pcfaktor.androiddev.PAGE_BOOKMARKS
import com.pcfaktor.androiddev.PAGE_FEED
import com.pcfaktor.androiddev.R
import com.pcfaktor.androiddev.databinding.FragmentFullArticleBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

const val KEY_LINK = "articleLink"

class FullArticleFragment : Fragment() {

    companion object {
        fun newInstance(link: String) = FullArticleFragment().also {
            val bundle = Bundle()
            bundle.putString(KEY_LINK, link)
            it.arguments = bundle
        }
    }

    private val viewModel: FullArticleViewModel by viewModel()
    private val binding: FragmentFullArticleBinding get() = _binding!!
    private var _binding: FragmentFullArticleBinding? = null
    private lateinit var webView: WebView
    private lateinit var receiver: BroadcastReceiver

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFullArticleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        webView = binding.wvFullArticle
        if (savedInstanceState != null)
            viewModel.link.value = savedInstanceState.getString(KEY_LINK)
        else
            viewModel.link.value = arguments?.getString(KEY_LINK) ?: ERROR_LINK
        initViews()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initViews() {
        if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK)) {
            WebSettingsCompat.setForceDark(webView.settings, WebSettingsCompat.FORCE_DARK_ON)
        }
        webView.settings.javaScriptEnabled = true
        webView.setBackgroundColor(resources.getColor(R.color.primaryDarkColor))
        webView.webViewClient = WebViewClient()
//        arguments?.let {
//            viewModel.link.value = this.arguments?.getString(KEY_LINK) ?: BASE_URL
//        }

        viewModel.link.observe(viewLifecycleOwner, {
            webView.loadUrl(it)
        })
    }

    override fun onStart() {
        super.onStart()
        receiveLink()
    }

    private fun receiveLink() {
        val intentFilter = IntentFilter().also {
            it.addAction(PAGE_FEED)
            it.addAction(PAGE_BOOKMARKS)
            it.addAction(PAGE_ARTICLE)
        }
        receiver = object : BroadcastReceiver() {

            override fun onReceive(context: Context?, intent: Intent?) {
                when (intent?.action) {
                    PAGE_ARTICLE -> {
                        viewModel.link.value =
                            intent.extras?.getString(KEY_LINK) ?: ERROR_LINK
                    }
                }
            }
        }
        activity?.registerReceiver(receiver, intentFilter)
    }

    override fun onStop() {
        super.onStop()
        activity?.unregisterReceiver(receiver)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_LINK, viewModel.link.value)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}