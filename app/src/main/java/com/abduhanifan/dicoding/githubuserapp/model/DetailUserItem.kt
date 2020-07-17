package com.abduhanifan.dicoding.githubuserapp.model

data class DetailUserItem(
    var id: Int = 0,
    var login: String? = null,
    var avatar_url: String? = null,
    var username: String? = null,
    var company: String? = null,
    var location: String? = null,
    var public_repos: String? = null,
    var followers: String? = null,
    var following: String? = null
)