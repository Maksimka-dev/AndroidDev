package com.pcfaktor.androiddev.ui.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.pcfaktor.androiddev.databinding.FragmentFeedBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class FeedFragment : Fragment() {

    companion object {
        fun newInstance() = FeedFragment()
    }

    private var _binding: FragmentFeedBinding? = null
    private val binding: FragmentFeedBinding get() = _binding!!
    private val viewModel by viewModel<FeedViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFeedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.i("onViewCreated()")
    }
}