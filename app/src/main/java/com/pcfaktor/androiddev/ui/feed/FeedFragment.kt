package com.pcfaktor.androiddev.ui.feed

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pcfaktor.androiddev.AppActivity
import com.pcfaktor.androiddev.PAGE_ARTICLE
import com.pcfaktor.androiddev.databinding.FragmentFeedBinding
import com.pcfaktor.androiddev.ui.full_article.KEY_LINK
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class FeedFragment : Fragment() {

    companion object {
        fun newInstance() = FeedFragment()
    }

    private var _binding: FragmentFeedBinding? = null
    private val binding: FragmentFeedBinding get() = _binding!!
    private val viewModel by viewModel<FeedViewModel>()
    private lateinit var adapter: FeedAdapter
    private lateinit var recycler: RecyclerView
    private var activity: AppActivity? = null

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
        activity = getActivity() as AppActivity
        recycler = binding.recyclerFeed
        recycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        adapter = FeedAdapter({ onClickItem(it) }, { onLongClickItem(it) })
        initViews()
    }

    private fun initViews() {
        viewModel.articlesLiveData.observe(viewLifecycleOwner, { adapter.submitList(it) })
        recycler.adapter = adapter
    }

    private fun onLongClickItem(position: Int) {
        Timber.i("onLongClick(position:$position)")
    }

    private fun onClickItem(position: Int) {
        Timber.i("onBnClick(position:$position)")
        val link = viewModel.articlesLiveData.value?.get(position)?.readMoreReference ?: ""
        val intent = Intent(PAGE_ARTICLE)
        intent.putExtra(KEY_LINK, link)
        context?.sendBroadcast(intent)
    }

    override fun onStart() {
        super.onStart()
        viewModel.onStart()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}