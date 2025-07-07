package org.example.project.di

import org.example.project.data.remote.ApiService
import org.example.project.data.remote.KtorClientFactory
import org.example.project.data.repository.UserRepository
import org.example.project.user.UserViewModel
import org.koin.dsl.module

val appModule = module {
    single { KtorClientFactory.create() }
    single { ApiService(get()) }
    single { UserRepository(get()) }
    single { UserViewModel(get()) }
}