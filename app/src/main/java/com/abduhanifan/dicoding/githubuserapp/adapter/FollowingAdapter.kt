package com.abduhanifan.dicoding.githubsearchuser.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abduhanifan.dicoding.githubsearchuser.R
import com.abduhanifan.dicoding.githubsearchuser.model.FollowingItem
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_following.view.*

class FollowingAdapter : RecyclerView.Adapter<FollowingAdapter.FollowingViewHolder>() {

    private val mData = ArrayList<FollowingItem>()

    fun setData(items: ArrayList<FollowingItem>) {
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): FollowingViewHolder {
        val mView = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_following, viewGroup, false)
        return FollowingViewHolder(mView)
    }

    override fun onBindViewHolder(followingViewHolder: FollowingViewHolder, position: Int) {
        followingViewHolder.bind(mData[position])
    }

    override fun getItemCount(): Int = mData.size

    inner class FollowingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(followingItems: FollowingItem) {
            with(itemView) {
                textLogin.text = followingItems.login
                textType.text = followingItems.type
                Glide.with(context)
                    .load(followingItems.avatar_url)
                    .apply(RequestOptions().override(56, 56))
                    .into(imgAvatar)
            }
        }
    }
}