package com.lid.chatapp.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.lid.chatapp.MainActivity
import com.lid.chatapp.R
import com.lid.chatapp.viewmodels.NewsViewModel
import kotlinx.android.synthetic.main.fragment_article.*


class ArticleFragment : Fragment(R.layout.fragment_article) {

    private val viewModel: NewsViewModel by viewModels()
    private val args: ArticleFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val article = args.article
        webView.apply {
            webViewClient = WebViewClient()
            article.url.let { article ->
                loadUrl(article)
            }
        }
    }
}