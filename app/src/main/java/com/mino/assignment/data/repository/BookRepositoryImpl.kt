package com.mino.assignment.data.repository

import com.mino.assignment.data.model.response.BookResponse
import com.mino.assignment.data.source.remote.BookRemoteDataSource
import io.reactivex.Single

class BookRepositoryImpl(private val remoteDataSource: BookRemoteDataSource) : BookRepository {
    override fun getBook(query: String, page: Int): Single<BookResponse> =
        remoteDataSource.getBook(query = query, page = page)

}