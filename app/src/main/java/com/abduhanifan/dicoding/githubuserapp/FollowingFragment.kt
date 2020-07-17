package com.abduhanifan.dicoding.githubuserapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.abduhanifan.dicoding.githubuserapp.adapter.FollowingAdapter
import com.abduhanifan.dicoding.githubuserapp.model.UserItem
import com.abduhanifan.dicoding.githubuserapp.viewModel.FollowingViewModel
import kotlinx.android.synthetic.main.fragment_following.*

class FollowingFragment : Fragment() {

    companion object {
        const val EXTRA_USER = "extra_user"
    }

    private lateinit var adapter: FollowingAdapter
    private lateinit var followingViewModel: FollowingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        showListFollowing()

        followingViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(FollowingViewModel::class.java)

        val username = activity?.intent?.getParcelableExtra(EXTRA_USER) as UserItem

        showLoading(true)

        followingViewModel.setFollowing(username.login.toString())
        followingViewModel.getFollowing().observe(viewLifecycleOwner, Observer { followingItem ->
            if (followingItem != null) {
                adapter.setData(followingItem)
                showLoading(false)
            }
        })
    }

    private fun showListFollowing() {
        adapter = FollowingAdapter()
        adapter.notifyDataSetChanged()

        rv_Following.layoutManager = LinearLayoutManager(context)
        rv_Following.adapter = adapter
        rv_Following.setHasFixedSize(true)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }
}