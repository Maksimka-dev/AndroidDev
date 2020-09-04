package com.pcfaktor.androiddev.ui.full_article

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.webkit.WebSettingsCompat
import androidx.webkit.WebViewFeature
import com.pcfaktor.androiddev.R
import com.pcfaktor.androiddev.data.network.ApiService.BASE_URL
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFullArticleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        webView = binding.wvFullArticle
        initViews()
    }

    private fun initViews() {
        if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK)) {
            WebSettingsCompat.setForceDark(webView.settings, WebSettingsCompat.FORCE_DARK_ON);
        }
        webView.settings.javaScriptEnabled = true
        webView.setBackgroundColor(resources.getColor(R.color.primaryDarkColor))
        webView.webViewClient = WebViewClient()
        arguments?.let {
            val link = this.arguments?.getString(KEY_LINK) ?: BASE_URL
            webView.loadUrl(link)
        }
        webView.progress
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}