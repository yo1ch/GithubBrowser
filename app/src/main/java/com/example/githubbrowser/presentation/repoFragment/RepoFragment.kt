package com.example.githubbrowser.presentation.repoFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubbrowser.databinding.FragmentRepoBinding
import com.example.githubbrowser.domain.entity.EntryType
import com.example.githubbrowser.presentation.repoFragment.adapter.RepoListAdapter
import com.example.githubbrowser.presentation.viewModels.RepoFragmentViewModel
import com.example.githubbrowser.presentation.webViewFragment.WebViewFragmentArgs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class RepoFragment : Fragment() {

    private var _binding: FragmentRepoBinding? = null
    private val binding
        get() = _binding ?: throw UnsupportedOperationException("FragmentRepoBinding == null")

    private val viewModel by viewModels<RepoFragmentViewModel>()

    @Inject
    lateinit var adapter: RepoListAdapter

    private val args by navArgs<RepoFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                viewModel.onBackClicked()
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRepoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        initListeners()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.repositoryStructure.onEach { repoStructure ->
            repoStructure?.let {
                adapter.submitList(it.entries)
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.shouldPopBackStack.onEach { shouldPopBackstack ->
            if (shouldPopBackstack) findNavController().popBackStack()
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.repoStructureList.layoutManager = layoutManager
        binding.repoStructureList.adapter = adapter
        binding.repoStructureList.addItemDecoration(
            DividerItemDecoration(
                activity,
                layoutManager.orientation
            )
        )
    }


    private fun initListeners() {
        adapter.onClickListener = { entry ->
            when (entry.type) {
                EntryType.FILE -> showFileInWebView(entry.htmlUrl)
                EntryType.DIR -> viewModel.fetchRepositoryStructure(path = entry.path)
            }

        }
    }

    private fun showFileInWebView(url: String) {
        findNavController()
            .navigate(RepoFragmentDirections.actionRepoFragmentToWebViewFragment(url = url))
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}