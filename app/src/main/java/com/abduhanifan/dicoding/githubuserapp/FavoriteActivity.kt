package com.abduhanifan.dicoding.githubuserapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.abduhanifan.dicoding.githubuserapp.DetailActivity
import com.abduhanifan.dicoding.githubuserapp.adapter.FavoriteAdapter
import com.abduhanifan.dicoding.githubuserapp.db.FavoriteHelper
import com.abduhanifan.dicoding.githubuserapp.helper.MappingHelper
import com.abduhanifan.dicoding.githubuserapp.model.FavoriteItem
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_favorite.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteActivity : AppCompatActivity() {

    private lateinit var adapter: FavoriteAdapter
    private lateinit var favoriteHelper: FavoriteHelper

    companion object {
        const val EXTRA_LOGIN = "extra_login"
        const val EXTRA_AVATAR = "extra_avatar_url"
        const val EXTRA_TYPE = "extra_type"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        supportActionBar?.setTitle(R.string.activity_favorite)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        showListFavorite()

        favoriteHelper = FavoriteHelper.getInstance(applicationContext)
        favoriteHelper.open()

        if (savedInstanceState == null) {
            loadFavoriteAsync()              // Proses ambil data
        } else {
            val list = savedInstanceState.getParcelableArrayList<FavoriteItem>(EXTRA_LOGIN)
            if (list != null) {
                adapter.listFavorite = list
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.apply {
            putParcelableArrayList(EXTRA_LOGIN, adapter.listFavorite)
            putParcelableArrayList(EXTRA_AVATAR, adapter.listFavorite)
            putParcelableArrayList(EXTRA_TYPE, adapter.listFavorite)
        }
    }

    private fun showListFavorite() {
        rv_favorite.layoutManager =LinearLayoutManager(this)
        rv_favorite.setHasFixedSize(true)

        adapter = FavoriteAdapter {
            val intent = Intent(this@FavoriteActivity, DetailActivity::class.java)
                intent.apply {
                    putExtra(EXTRA_LOGIN, it.login)
                    putExtra(EXTRA_AVATAR, it.avatar_url)
                    putExtra(EXTRA_TYPE, it.type)
                }
            startActivity(intent)
        }
        rv_favorite.adapter = adapter
    }

    private fun loadFavoriteAsync() {
        GlobalScope.launch(Dispatchers.Main) {
//            progressBar.visibility = View.VISIBLE
            val deferredFavorite = async(Dispatchers.IO) {
                val cursor = favoriteHelper.queryAll()
                MappingHelper.mapCursorToArrayList(cursor)
            }
//            progressBar.visibility = View.INVISIBLE
            val favorite = deferredFavorite.await()
            if (favorite.size > 0) {
                adapter.listFavorite = favorite
            } else {
                adapter.listFavorite = ArrayList()
                Snackbar.make(rv_favorite, "Tidak ada data", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        showListFavorite()
        loadFavoriteAsync()
    }

    override fun onDestroy() {
        super.onDestroy()
        favoriteHelper.close()
    }
}