package com.zs.my_zs_jetpack.ui.webDetails

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zs.my_zs_jetpack.common_base.BaseModel

class WebDetailsViewModel : BaseModel() {
    /**
     * webView 进度
     */
    private var _progress = MutableLiveData<Int>()
    val progress: LiveData<Int> = _progress
    fun setProgress(value: Int) {
        _progress.value = value
    }

    /**
     * 最大 进度
     */
    private var _maxProgress = MutableLiveData<Int>()
    val maxProgress: LiveData<Int> = _maxProgress
    fun setMaxProgress(value: Int) {
        _maxProgress.value = value
    }

    /**
     * progress是否隐藏
     */
    private var _isVisible = MutableLiveData<Boolean>()
    val isVisible: LiveData<Boolean> = _isVisible
    fun setIsVisible(value: Boolean) {
        _isVisible.value = value
    }
}