package com.abduhanifan.dicoding.githubsearchuser.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abduhanifan.dicoding.githubsearchuser.R
import com.abduhanifan.dicoding.githubsearchuser.model.UserItem
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_user.view.*

class UserAdapter(private val listener: (UserItem) -> Unit) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private val mData = ArrayList<UserItem>()

    fun setData(items: ArrayList<UserItem>) {
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): UserViewHolder {
        val mView = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_user, viewGroup, false)
        return UserViewHolder(mView)
    }

    override fun onBindViewHolder(userViewHolder: UserViewHolder, position: Int) {
        userViewHolder.bind(mData[position], listener)
    }

    override fun getItemCount(): Int = mData.size

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(userItems: UserItem, listener: (UserItem) -> Unit) {
            with(itemView) {
                textLogin.text = userItems.login
                textType.text = userItems.type
                Glide.with(context)
                    .load(userItems.avatar_url)
                    .apply(RequestOptions().override(56, 56))
                    .into(imgAvatar)
                setOnClickListener { listener(userItems) }
            }
        }
    }
}