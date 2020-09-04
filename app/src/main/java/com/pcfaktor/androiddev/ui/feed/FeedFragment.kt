package com.pcfaktor.androiddev.ui.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pcfaktor.androiddev.R
import com.pcfaktor.androiddev.databinding.FragmentFeedBinding
import com.pcfaktor.androiddev.ui.full_article.FullArticleFragment
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
        recycler = binding.recyclerFeed
        recycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        adapter = FeedAdapter { onClickItem(it) }
        initViews()
    }

    private fun initViews() {
        viewModel.articlesLiveData.observe(viewLifecycleOwner, { adapter.submitList(it) })
        recycler.adapter = adapter
    }

    private fun onClickItem(position: Int) {
        val link = viewModel.articlesLiveData.value?.get(position)?.readMoreReference ?: ""
        parentFragmentManager.beginTransaction()
            .replace(R.id.container, FullArticleFragment.newInstance(link))
            .addToBackStack(null)
            .commit()
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