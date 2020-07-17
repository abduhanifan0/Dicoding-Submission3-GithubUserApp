package com.abduhanifan.dicoding.githubsearchuser.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abduhanifan.dicoding.githubsearchuser.R
import com.abduhanifan.dicoding.githubsearchuser.model.FollowerItem
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_follower.view.*

class FollowerAdapter : RecyclerView.Adapter<FollowerAdapter.FollowerViewHolder>() {

    private val mData = ArrayList<FollowerItem>()

    fun setData(items: ArrayList<FollowerItem>) {
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): FollowerViewHolder {
        val mView = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_follower, viewGroup, false)
        return FollowerViewHolder(mView)
    }

    override fun onBindViewHolder(followerViewHolder: FollowerViewHolder, position: Int) {
        followerViewHolder.bind(mData[position])
    }

    override fun getItemCount(): Int = mData.size

    inner class FollowerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(followerItems: FollowerItem) {
            with(itemView) {
                textLogin.text = followerItems.login
                textType.text = followerItems.type
                Glide.with(context)
                    .load(followerItems.avatar_url)
                    .apply(RequestOptions().override(56, 56))
                    .into(imgAvatar)
            }
        }
    }
}