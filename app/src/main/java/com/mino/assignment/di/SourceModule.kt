package com.mino.assignment.di

import com.mino.assignment.data.source.remote.BookRemoteDataSource
import com.mino.assignment.data.source.remote.BookRemoteDataSourceImpl
import org.koin.dsl.module

val sourceModule = module {
        single<BookRemoteDataSource> { BookRemoteDataSourceImpl(get()) }
}