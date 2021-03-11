package com.mino.assignment.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mino.assignment.Event
import com.mino.assignment.RxEventBus
import com.mino.assignment.basic.BaseViewModel
import com.mino.assignment.data.model.DocumentModel
import com.mino.assignment.data.model.MetaModel
import com.mino.assignment.data.repository.BookRepository
import com.mino.assignment.networkSchedulers
import com.mino.assignment.plusAssign

class BookViewModel(private val bookRepository: BookRepository) : BaseViewModel() {

    private val _documentModelList = MutableLiveData<Event<Pair<Int, List<DocumentModel>>>>()
    val documentModelList: LiveData<Event<Pair<Int, List<DocumentModel>>>> get() = _documentModelList

    private val _metaModel = MutableLiveData<Event<MetaModel>>()
    val metaModel: LiveData<Event<MetaModel>> get() = _metaModel

    val query = MutableLiveData<Event<String>>()


//    단일 이벤트의 실행은 SingleLiveEvent 혹은 Event Wrapper 클래스를 사용하자

    fun getBook(query: String, page: Int = 1) {
        if (query.isNotBlank()) {
            compositeDisposable += bookRepository.getBook(query = query, page = page)
                .networkSchedulers()
                .subscribe({ bookResponse ->
                    if (bookResponse.isEmpty()) {
                        RxEventBus.sendEvent(RxEventBus.NO_DATA)
                    } else {
                        RxEventBus.sendEvent(RxEventBus.HAVE_DATA)
                    }
                    _documentModelList.value = Event(page to bookResponse.documentsToModelList())
                    _metaModel.value = Event(bookResponse.metaToModel())
                }, {
                    Log.e("asdasd", it.message.toString())
                    Log.e("asdasd", it.cause.toString())
                })
        }
    }


}
