package com.example.githubbrowser.presentation.searchFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.githubbrowser.databinding.FragmentSearchBinding
import com.example.githubbrowser.domain.entity.SearchResult
import com.example.githubbrowser.presentation.searchFragment.listAdapter.SearchResultListAdapter
import com.example.githubbrowser.presentation.viewModels.SearchFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding
        get() = _binding ?: throw UnsupportedOperationException("FragmentSearchBinding == null")

    private val viewModel by viewModels<SearchFragmentViewModel>()

    @Inject
    lateinit var adapter: SearchResultListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() {
        binding.resultList.adapter = adapter
        adapter.onClickListener = { searchResult ->
            when (searchResult) {
                is SearchResult.Repository -> {

                }

                is SearchResult.User -> {

                }
            }
        }
        adapter.submitList(
            listOf(
                SearchResult.User(
                    id = 1,
                    avatarUrl = "",
                    login = "Novohudonosor",
                    score = "123"
                ),
                SearchResult.Repository(
                    id = 1,
                    name = "Stacy Deleon",
                    forksCount = "45",
                    description = "luptatumluptatumluptatumluptatumluptatumluptatumluptatumluptatumluptatumluptatumluptatumluptatumluptatumluptatumluptatumluptatum"
                ),
                SearchResult.User(
                    id = 2,
                    avatarUrl = "",
                    login = "Novohudonosor",
                    score = "123"
                ),
                SearchResult.Repository(
                    id = 2,
                    name = "Stacy Deleon",
                    forksCount = "45",
                    description = "luptatumluptatumluptatumluptatumluptatumluptatumluptatumluptatumluptatumluptatumluptatumluptatumluptatumluptatumluptatumluptatum"
                ),
            )
        )
    }


}