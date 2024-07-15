package com.example.githubbrowser.presentation.searchFragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.githubbrowser.R
import com.example.githubbrowser.databinding.FragmentSearchBinding
import com.example.githubbrowser.domain.entity.SearchResult
import com.example.githubbrowser.presentation.searchFragment.adapter.SearchResultListAdapter
import com.example.githubbrowser.presentation.utils.getQueryChangeFlow
import com.example.githubbrowser.presentation.viewModels.SearchFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeViewModel()
        initListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeViewModel() {
        viewModel.searchResult
            .onEach { list ->
                adapter.submitList(list)
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun initListeners() {
        binding.searchEditText
            .getQueryChangeFlow()
            .onEach { query ->
                binding.searchButton.isEnabled = query.length >= 3
            }.launchIn(lifecycleScope)
        binding.searchButton.setOnClickListener {
            viewModel.getSearchData(binding.searchEditText.text.toString())
        }

    }

    private fun setupRecyclerView() {
        binding.resultList.adapter = adapter
        adapter.onClickListener = { searchResult ->
            when (searchResult) {
                is SearchResult.Repository -> {
                    showRepositoryStructure(
                        repoOwner = searchResult.ownerLogin,
                        repoName = searchResult.name
                    )
                }

                is SearchResult.User -> {
                    openInBrowser(searchResult.htmlUrl)
                }
            }
        }
    }

    private fun showRepositoryStructure(
        repoOwner: String,
        repoName: String,
    ) {
        findNavController().navigate(
            SearchFragmentDirections.actionSearchFragmentToRepoFragment(
                owner = repoOwner, repo = repoName
            )
        )
    }

    private fun openInBrowser(url: String) {
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(url)
        )
        if (intent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(intent)
        } else {
            showToast(getString(R.string.browser_not_installed_warning))
        }
    }

    private fun showToast(text: String) {
        Toast.makeText(requireContext(), text, Toast.LENGTH_LONG).show()
    }


}