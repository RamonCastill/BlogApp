package com.rrstudio.blogapp.domain

import com.rrstudio.blogapp.core.Resource
import com.rrstudio.blogapp.data.model.Post
import com.rrstudio.blogapp.data.remote.HomeScreenDataSource

class HomeScreenRepoImpl(private val dataSource: HomeScreenDataSource): HomeScreenRepo {

    override suspend fun getLatestPosts(): Resource<List<Post>> = dataSource.getLatestPosts()
}