package com.mino.assignment.data.source.remote

import com.mino.assignment.data.model.response.BookResponse
import com.mino.assignment.data.source.remote.network.BookApi
import io.reactivex.Single

class BookRemoteDataSourceImpl(private val bookApi: BookApi) : BookRemoteDataSource {
    override fun getBook(query: String, page: Int): Single<BookResponse> =
        bookApi.getBook(query = query, page = page)
}