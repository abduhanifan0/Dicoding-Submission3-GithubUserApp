package com.abduhanifan.dicoding.githubsearchuser.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.abduhanifan.dicoding.githubsearchuser.model.FollowingItem
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray

class FollowingViewModel : ViewModel() {

    private val listFollowing = MutableLiveData<ArrayList<FollowingItem>>()

    fun setFollowing(username: String) {
        //Request API
        val listItem = ArrayList<FollowingItem>()

        val url = "https://api.github.com/users/$username/following"

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
                        val following = responseArray.getJSONObject(i)
                        val followingItem = FollowingItem()
                        followingItem.id = following.getInt("id")
                        followingItem.login = following.getString("login")
                        followingItem.avatar_url = following.getString("avatar_url")
                        followingItem.type = following.getString("type")
                        listItem.add(followingItem)
                    }

                    listFollowing.postValue(listItem)
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

    fun getFollowing(): LiveData<ArrayList<FollowingItem>> {
        return listFollowing
    }
}