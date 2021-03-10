package com.mino.assignment.data.repository

import com.mino.assignment.data.model.response.BookResponse
import io.reactivex.Single

interface BookRepository {
    fun getBook(query : String, page : Int) : Single<BookResponse>
}