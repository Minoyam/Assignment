package com.mino.assignment.di

import com.mino.assignment.viewmodel.BookViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { BookViewModel(get()) }
}