package com.rrstudio.blogapp.domain.home

import com.rrstudio.blogapp.core.Resource
import com.rrstudio.blogapp.data.model.Post

interface HomeScreenRepo {
    suspend fun getLatestPosts(): Resource<List<Post>>
}