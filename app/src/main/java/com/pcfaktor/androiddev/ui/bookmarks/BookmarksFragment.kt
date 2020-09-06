package com.pcfaktor.androiddev.ui.bookmarks

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pcfaktor.androiddev.PAGE_ARTICLE
import com.pcfaktor.androiddev.databinding.FragmentFeedBinding
import com.pcfaktor.androiddev.ui.feed.FeedAdapter
import com.pcfaktor.androiddev.ui.full_article.KEY_LINK
import org.koin.androidx.viewmodel.ext.android.viewModel

class BookmarksFragment : Fragment() {

    companion object {
        fun newInstance() = BookmarksFragment()
    }

    private var _binding: FragmentFeedBinding? = null
    private val binding: FragmentFeedBinding get() = _binding!!
    private val viewModel by viewModel<BookmarksViewModel>()
    private lateinit var adapter: FeedAdapter
    private lateinit var recycler: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFeedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler = binding.recyclerFeed
        recycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        adapter = FeedAdapter({ onClickItem(it) }, { onLongClickItem(it) })
        initViews()
    }

    private fun initViews() {
        viewModel.bookmarksLiveData.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
            adapter.notifyDataSetChanged()
            if (it.isNotEmpty()) {
                binding.groupFeed.visibility = View.GONE
            } else
                binding.groupFeed.visibility = View.VISIBLE
        })
        recycler.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume()
    }

    private fun onClickItem(position: Int) {
        val link = viewModel.bookmarksLiveData.value?.get(position)?.readMoreLink ?: ""
        val intent = Intent(PAGE_ARTICLE)
        intent.putExtra(KEY_LINK, link)
        context?.sendBroadcast(intent)
    }

    private fun onLongClickItem(position: Int) {
        val article = viewModel.bookmarksLiveData.value?.get(position)
        if (article != null)
            viewModel.deleteBookmark(article)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}