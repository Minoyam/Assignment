package com.mino.assignment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mino.assignment.utils.Event
import com.mino.assignment.basic.BaseViewModel
import com.mino.assignment.data.model.DocumentModel
import com.mino.assignment.data.model.MetaModel
import com.mino.assignment.data.repository.BookRepository
import com.mino.assignment.utils.networkSchedulers
import com.mino.assignment.utils.plusAssign

class BookViewModel(private val bookRepository: BookRepository) : BaseViewModel() {

    private val _documentModelList = MutableLiveData<Event<Pair<Int, List<DocumentModel>>>>()
    val documentModelList: LiveData<Event<Pair<Int, List<DocumentModel>>>> get() = _documentModelList

    private val _metaModel = MutableLiveData<Event<MetaModel>>()
    val metaModel: LiveData<Event<MetaModel>> get() = _metaModel

    private val _errorMessage = MutableLiveData<Event<String>>()
    val errorMessage: LiveData<Event<String>> get() = _errorMessage

    private val _isData = MutableLiveData<Event<Boolean>>()
    val isData : LiveData<Event<Boolean>> get() = _isData

    val query = MutableLiveData<Event<String>>()


    fun getBook(query: String, page: Int = 1) {
        if (query.isNotBlank()) {
            compositeDisposable += bookRepository.getBook(query = query, page = page)
                .networkSchedulers()
                .subscribe({ bookResponse ->
                    _isData.value = Event(bookResponse.isEmpty())
                    _documentModelList.value = Event(page to bookResponse.documentsToModelList())
                    _metaModel.value = Event(bookResponse.metaToModel())
                }, { throwable ->
                    _errorMessage.value = Event(throwable.message.toString())
                })
        }
    }


}
