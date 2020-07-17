package com.abduhanifan.dicoding.githubsearchuser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.abduhanifan.dicoding.githubsearchuser.adapter.FollowerAdapter
import com.abduhanifan.dicoding.githubsearchuser.model.UserItem
import com.abduhanifan.dicoding.githubsearchuser.viewModel.FollowerViewModel
import kotlinx.android.synthetic.main.fragment_follower.*

class FollowerFragment : Fragment() {

    companion object {
        const val EXTRA_USER = "extra_user"
    }

    private lateinit var adapter: FollowerAdapter
    private lateinit var followerViewModel: FollowerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_follower, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        showListFollower()

        followerViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
            .get(FollowerViewModel::class.java)

        val username = activity?.intent?.getParcelableExtra(EXTRA_USER) as UserItem

        showLoading(true)

        followerViewModel.setFollowers(username.login.toString())
        followerViewModel.getFollowers().observe(viewLifecycleOwner, Observer { followerItem ->
            if (followerItem != null) {
                adapter.setData(followerItem)
                showLoading(false)
            }
        })
    }

    private fun showListFollower() {
        adapter = FollowerAdapter()
        adapter.notifyDataSetChanged()

        rv_Follower.layoutManager = LinearLayoutManager(context)
        rv_Follower.adapter = adapter
        rv_Follower.setHasFixedSize(true)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }
}
