package com.example.githubbrowser.presentation.searchFragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.example.githubbrowser.R
import com.example.githubbrowser.databinding.FragmentSearchBinding
import com.example.githubbrowser.domain.entity.SearchResult
import com.example.githubbrowser.presentation.searchFragment.adapter.SearchResultListAdapter
import com.example.githubbrowser.presentation.utils.getQueryChangeFlow
import com.example.githubbrowser.presentation.utils.hideKeyboard
import com.example.githubbrowser.presentation.viewModels.SearchFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@ExperimentalCoroutinesApi
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
        initAdapter()
        observeViewModel()
        initListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.test.collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private fun initListeners() {
        binding.searchEditText
            .getQueryChangeFlow()
            .onEach { query ->
                binding.searchButton.isEnabled = query.length >= 3
            }.launchIn(lifecycleScope)
        binding.searchEditText.setOnEditorActionListener(object : OnEditorActionListener{
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    binding.searchButton.performClick()
                    return true
                }
                return false
            }

        })
    }

    private fun initAdapter() {
        binding.resultList.adapter = adapter
        binding.searchButton.setOnClickListener {
            viewModel.searchByQuery(binding.searchEditText.text.toString())
            hideKeyboard()
        }
        adapter.addLoadStateListener { loadState ->
            binding.resultList.isVisible = loadState.source.refresh is LoadState.NotLoading
            binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
            binding.error.root.isVisible = loadState.source.refresh is LoadState.Error
            handleError(loadState)
        }
        binding.error.buttonRetry.setOnClickListener {
            adapter.retry()
        }
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

    private fun handleError(loadState: CombinedLoadStates) {
        val errorState = loadState.source.append as? LoadState.Error
            ?: loadState.source.prepend as? LoadState.Error
        errorState?.let {
            showToast(text = errorState.error.toString())
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