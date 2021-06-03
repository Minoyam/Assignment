package com.mino.assignment.di

import com.mino.assignment.data.repository.BookRepository
import com.mino.assignment.data.repository.BookRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<BookRepository> { BookRepositoryImpl(get()) }
}