package com.pcfaktor.androiddev.ui.bookmarks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pcfaktor.androiddev.databinding.FragmentFeedBinding
import com.pcfaktor.androiddev.ui.feed.FeedAdapter
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
    }

    private fun onClickItem(position: Int) {
    }

    private fun onLongClickItem(position: Int) {
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}