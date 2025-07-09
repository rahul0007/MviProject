package org.example.project.di

import org.example.project.data.network.ApiService
import org.example.project.data.network.KtorClientFactory
import org.example.project.data.repository.UserRepository
import org.example.project.domain.usecase.GetUsersUseCase
import org.example.project.pagination.LoadMoreUserViewModel
import org.example.project.user.UserListViewModel
import org.koin.dsl.module

val appModule = module {
    single { KtorClientFactory.create() }
    single { ApiService(get()) }
    single { UserRepository(get()) }
    single { UserListViewModel(get()) }
    single { LoadMoreUserViewModel(get()) }
    single { GetUsersUseCase(get()) }
}