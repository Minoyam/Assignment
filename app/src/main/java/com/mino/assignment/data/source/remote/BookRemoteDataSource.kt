package com.mino.assignment.data.source.remote

import com.mino.assignment.data.model.response.BookResponse
import io.reactivex.Single

interface BookRemoteDataSource  {
    fun getBook(query: String, page: Int): Single<BookResponse>
}