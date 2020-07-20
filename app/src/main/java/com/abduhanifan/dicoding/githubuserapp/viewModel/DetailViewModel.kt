package com.abduhanifan.dicoding.githubuserapp.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.abduhanifan.dicoding.githubuserapp.model.DetailUserItem
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class DetailViewModel : ViewModel() {
    private val listDetailUser = MutableLiveData<ArrayList<DetailUserItem>>()

    fun setDetailUser(username: String) {
        //Request API
        val listItem = ArrayList<DetailUserItem>()

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

                    val detailUserItem = DetailUserItem()
                    detailUserItem.id = responseObject.getInt("id")
                    detailUserItem.login = responseObject.getString("login")
                    detailUserItem.avatar_url = responseObject.getString("avatar_url")
                    detailUserItem.name = responseObject.getString("name")
                    detailUserItem.company = responseObject.getString("company")
                    detailUserItem.location = responseObject.getString("location")
                    detailUserItem.public_repos = responseObject.getString("public_repos")
                    detailUserItem.followers = responseObject.getString("followers")
                    detailUserItem.following = responseObject.getString("following")
                    listItem.add(detailUserItem)

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

    fun getDetailUser(): LiveData<ArrayList<DetailUserItem>> {
        return listDetailUser
    }
}