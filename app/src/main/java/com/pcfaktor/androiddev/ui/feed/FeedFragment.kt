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
import com.pcfaktor.androiddev.R
import com.pcfaktor.androiddev.databinding.FragmentFeedBinding
import com.pcfaktor.androiddev.ui.full_article.KEY_LINK
import org.koin.androidx.viewmodel.ext.android.viewModel

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
        viewModel.articlesLiveData.observe(viewLifecycleOwner, {
            adapter.submitList(it)
            adapter.notifyDataSetChanged()
            setContentStub()
        })
        recycler.adapter = adapter
    }

    private fun setContentStub() {
        if (adapter.currentList.size == 0) {
            binding.titleWithStroke.text =
                resources.getString(R.string.title_my_feed)
            binding.tvNote.text = resources.getString(R.string.note_empty_feed)
            binding.groupFeed.visibility = View.VISIBLE
        } else {
            binding.groupFeed.visibility = View.GONE
        }
    }

    private fun onLongClickItem(position: Int) {
        val article = viewModel.articlesLiveData.value?.get(position)
        if (article != null)
            viewModel.updateBookmarks(article)
    }

    private fun onClickItem(position: Int) {
        val link = viewModel.articlesLiveData.value?.get(position)?.readMoreLink ?: ""
        val intent = Intent(PAGE_ARTICLE)
        intent.putExtra(KEY_LINK, link)
        context?.sendBroadcast(intent)
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}