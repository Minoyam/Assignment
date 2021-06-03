package com.mino.assignment.data.source.remote.network

import com.mino.assignment.data.model.response.BookResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


interface BookApi {

    companion object {
        const val REST_API_KEY = "dde27df72b93d138a2bfdc63c3fde0a1"
        const val BASE_URL = "https://dapi.kakao.com"
    }

    @GET("/v3/search/book")
    fun getBook(
        @Query("target") target: String = "title",
        @Query("size") size: Int = 50,
        @Query("query") query: String,
        @Query("page") page : Int
    ): Single<BookResponse>
}
