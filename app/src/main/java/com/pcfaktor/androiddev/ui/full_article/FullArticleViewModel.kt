package com.pcfaktor.androiddev.ui.full_article

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FullArticleViewModel : ViewModel() {

    val link = MutableLiveData<String>()
}