package com.zs.my_zs_jetpack.ui.webDetails

import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.zs.my_zs_jetpack.R
import com.zs.my_zs_jetpack.common_base.BaseFragment
import com.zs.my_zs_jetpack.databinding.FragmentWebBinding
import com.zs.my_zs_jetpack.extension.clickNoRepeat


class WebFragment : BaseFragment<FragmentWebBinding>() {
    private val webVM by viewModels<WebDetailsViewModel>()
    private lateinit var loadUrl: String
    private lateinit var title: String

    override fun init() {
        super.init()
        binding.vm = webVM
        loadUrl = arguments?.getString("loadUrl").toString()
        title = arguments?.getString("title").toString()
        initWebView()
    }

    override fun onclick() {
        super.onclick()
        binding.ivBack.clickNoRepeat {
//            findNavController().navigateUp()
            if (binding.webView.canGoBack()) {
                //返回上个页面
                binding.webView.goBack()
            } else {
                //退出H5界面
                findNavController().navigateUp()
            }
        }
    }


    private fun initWebView() {
        binding.tvTitle.text = title

//        val webSettings: WebSettings = binding.webView.settings
//        webSettings.javaScriptEnabled = true
//        //自适应屏幕
//        binding.webView.settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
//        binding.webView.settings.loadWithOverviewMode = true

        //如果不设置WebViewClient，请求会跳转系统浏览器
        binding.webView.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                //返回false，意味着请求过程里，不管有多少次的跳转请求（即新的请求地址）
                //均交给webView自己处理，这也是此方法的默认处理
                return false
            }

            override fun shouldOverrideUrlLoading(
                view: WebView,
                request: WebResourceRequest
            ): Boolean {
                //返回false，意味着请求过程里，不管有多少次的跳转请求（即新的请求地址）
                //均交给webView自己处理，这也是此方法的默认处理
                return false
            }
        }

        binding.webView.loadUrl(loadUrl)

        //设置最大进度
        webVM.setMaxProgress(100)
        //webView加载成功回调
        binding.webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                //进度小于100，显示进度条
                if (newProgress < 100) {
                    webVM.setIsVisible(true)
                }
                //等于100隐藏
                else if (newProgress == 100) {
                    webVM.setIsVisible(false)
                }
                //改变进度
                webVM.setProgress(newProgress)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //自定义返回
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (binding.webView.canGoBack()) {
                        //返回上个页面
                        binding.webView.goBack()
                    } else {
                        //退出H5界面
                        findNavController().navigateUp()
                    }

                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun getLayoutId() = R.layout.fragment_web


}