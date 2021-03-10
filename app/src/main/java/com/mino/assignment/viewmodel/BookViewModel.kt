package com.mino.assignment.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mino.assignment.RxEventBus
import com.mino.assignment.basic.BaseViewModel
import com.mino.assignment.data.model.DocumentModel
import com.mino.assignment.data.model.MetaModel
import com.mino.assignment.data.repository.BookRepository
import com.mino.assignment.networkSchedulers
import com.mino.assignment.plusAssign

class BookViewModel(private val bookRepository: BookRepository) : BaseViewModel() {

    private val _documentModelList = MutableLiveData<List<DocumentModel>>()
    val documentModelList: LiveData<List<DocumentModel>> get() = _documentModelList

    private val _metaModel = MutableLiveData<MetaModel>()
    val metaModel: LiveData<MetaModel> get() = _metaModel

    private val _pageIndex = MutableLiveData<Int>()
    val pageIndex: LiveData<Int> get() = _pageIndex

    val query = MutableLiveData<String>()

    fun setPageIndex(page: Int) {
        _pageIndex.value = page
    }

    fun getBook(page: Int = 1) {
        query.value?.let { query ->
            if (query.isNotBlank()) {
                compositeDisposable += bookRepository.getBook(query = query, page = page)
                    .networkSchedulers()
                    .subscribe({ bookResponse ->
                        if (bookResponse.isEmpty()) {
                            RxEventBus.sendEvent(RxEventBus.NO_DATA)
                        } else {
                            RxEventBus.sendEvent(RxEventBus.HAVE_DATA)
                        }
                            _documentModelList.value = bookResponse.documentsToModelList()
                            Log.e("sdsdsdsd", _documentModelList.value?.size.toString())
                            _metaModel.value = bookResponse.metaToModel()
                    }, {
                        Log.e("asdasd", it.message.toString())
                        Log.e("asdasd", it.cause.toString())
                    })
            }
        }
    }


}
