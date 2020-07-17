package com.abduhanifan.dicoding.githubuserapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.abduhanifan.dicoding.githubuserapp.model.UserItem
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class DetailViewModel : ViewModel() {
    private val listDetailUser = MutableLiveData<ArrayList<UserItem>>()

    fun setDetailUser(username: String) {
        //Request API
        val listItem = ArrayList<UserItem>()

        val url = "https://api.github.com/users/$username"

        val client = AsyncHttpClient()
        client.addHeader("Authorization", "9eb6c532fc801bae706df9f0b12775c0c4ffeea4")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray
            ) {
                try {
                    //parsing JSON
                    val result = String(responseBody)
                    val responseObject = JSONObject(result)

                    val userItem = UserItem()
                    userItem.id = responseObject.getInt("id")
                    userItem.login = responseObject.getString("login")
                    userItem.avatar_url = responseObject.getString("avatar_url")
                    userItem.name = responseObject.getString("name")
                    userItem.company = responseObject.getString("company")
                    userItem.location = responseObject.getString("location")
                    userItem.public_repos = responseObject.getString("public_repos")
                    userItem.followers = responseObject.getString("followers")
                    userItem.following = responseObject.getString("following")
                    listItem.add(userItem)

                    listDetailUser.postValue(listItem)
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                Log.d("onFailure", error?.message.toString())
            }
        })
    }

    fun getDetailUser(): LiveData<ArrayList<UserItem>> {
        return listDetailUser
    }
}