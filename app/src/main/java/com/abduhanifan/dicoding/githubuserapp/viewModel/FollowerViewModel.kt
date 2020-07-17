package com.abduhanifan.dicoding.githubsearchuser.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.abduhanifan.dicoding.githubsearchuser.model.FollowerItem
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray

class FollowerViewModel : ViewModel() {

    private val listFollowers = MutableLiveData<ArrayList<FollowerItem>>()

    fun setFollowers(username: String) {
        //Request API
        val listItem = ArrayList<FollowerItem>()

        val url = "https://api.github.com/users/$username/followers"

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
                    val responseArray = JSONArray(result)

                    for (i in 0 until responseArray.length()) {
                        val follower = responseArray.getJSONObject(i)
                        val followerItem = FollowerItem()
                        followerItem.id = follower.getInt("id")
                        followerItem.login = follower.getString("login")
                        followerItem.avatar_url = follower.getString("avatar_url")
                        followerItem.type = follower.getString("type")
                        listItem.add(followerItem)
                    }

                    listFollowers.postValue(listItem)
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

    fun getFollowers(): LiveData<ArrayList<FollowerItem>> {
        return listFollowers
    }
}