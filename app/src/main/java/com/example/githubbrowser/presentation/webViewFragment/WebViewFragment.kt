package com.example.githubbrowser.presentation.webViewFragment


import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.githubbrowser.databinding.FragmentWebViewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WebViewFragment : Fragment() {

    private var _binding: FragmentWebViewBinding? = null
    private val binding
        get() = _binding ?: throw UnsupportedOperationException("FragmentWebViewBinding == null")

    private val args by navArgs<WebViewFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if(binding.webView.canGoBack()) binding.webView.goBack() else findNavController().popBackStack()
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWebViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupWebView()
        initListeners()
    }

    private fun initListeners(){
        binding.error.buttonRetry.setOnClickListener {
            binding.webView.loadUrl(args.url)
        }
    }

    private fun setupWebView() {
        binding.webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                return super.shouldOverrideUrlLoading(view, request)
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                showLoadingState()
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view,url)
                showSuccessState()
            }


            override fun onReceivedHttpError(
                view: WebView?,
                request: WebResourceRequest?,
                errorResponse: WebResourceResponse?
            ) {
                super.onReceivedHttpError(view, request, errorResponse)
                showErrorState(errorMessage = errorResponse?.reasonPhrase.toString())
            }

            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                super.onReceivedError(view, request, error)
                showErrorState(errorMessage = error?.description.toString())
            }


        }
        binding.webView.loadUrl(args.url)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showSuccessState() {
        with(binding) {
            error.root.isVisible = false
            webView.isVisible = true
            progressBar.isVisible = false
        }
    }

    private fun showLoadingState() {
        with(binding) {
            error.root.isVisible = false
            webView.isVisible = true
            progressBar.isVisible = true
        }
    }

    private fun showErrorState(errorMessage: String) {
        with(binding) {
            error.root.isVisible = true
            webView.isVisible = false
            progressBar.isVisible = false
            error.errorText.text = errorMessage
        }
    }
}

