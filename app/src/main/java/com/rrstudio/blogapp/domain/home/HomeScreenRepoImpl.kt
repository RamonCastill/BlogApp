package com.rrstudio.blogapp.domain.home

import com.rrstudio.blogapp.core.Resource
import com.rrstudio.blogapp.data.model.Post
import com.rrstudio.blogapp.data.remote.home.HomeScreenDataSource

class HomeScreenRepoImpl(private val dataSource: HomeScreenDataSource): HomeScreenRepo {

    override suspend fun getLatestPosts(): Resource<List<Post>> = dataSource.getLatestPosts()
}