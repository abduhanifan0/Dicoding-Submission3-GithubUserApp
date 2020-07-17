package com.abduhanifan.dicoding.githubuserapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.abduhanifan.dicoding.githubsearchuser.model.UserItem
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class MainViewModel : ViewModel() {

    private val listUser = MutableLiveData<ArrayList<UserItem>>()

    fun setUser(username: String) {
        //Request API
        val listItem = ArrayList<UserItem>()

        val url = "https://api.github.com/search/users?q=$username"

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
                    val list = responseObject.getJSONArray("items")

                    for (i in 0 until list.length()) {
                        val user = list.getJSONObject(i)
                        val userItem = UserItem()
                        userItem.id = user.getInt("id")
                        userItem.login = user.getString("login")
                        userItem.avatar_url = user.getString("avatar_url")
                        userItem.type = user.getString("type")
                        listItem.add(userItem)
                    }

                    listUser.postValue(listItem)
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

    fun getUser(): LiveData<ArrayList<UserItem>> {
        return listUser
    }
}