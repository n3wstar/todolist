package com.example.todos.remote.api

import com.example.todos.remote.dto.TodoDto
import com.example.todos.remote.dto.TodoListRequest
import com.example.todos.remote.dto.TodoListResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH

interface TodoApi {

    @GET("list")
    suspend fun getList(): TodoListResponse

    @PATCH("list")
    suspend fun updateList(
        @Header("X-Last-Known-Revision") revision: Int,
        @Body body: TodoListRequest
    ): TodoListResponse
}
