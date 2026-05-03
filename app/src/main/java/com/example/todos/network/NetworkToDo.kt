package com.example.todos.network

import com.example.todos.remote.api.TodoApi
import com.example.todos.remote.dto.TodoDto
import com.example.todos.remote.dto.TodoListResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkToDo {

    private const val BASE_URL = "https://hive.mrdekk.ru/todo/"
    private const val TOKEN = "71679800-2aee-40db-8ed0-41fe53aa9212"

    val api: TodoApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(
            OkHttpClient.Builder()
                .addInterceptor { chain ->
                    chain.proceed(
                        chain.request().newBuilder()
                            .addHeader("Authorization", "Bearer $TOKEN")
                            .build()
                    )
                }
                .build()
        )
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(TodoApi::class.java)
}