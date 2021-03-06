package com.quanda.moviedb.ui.screen.main.favoritemovie

import android.arch.lifecycle.ViewModelProviders
import android.graphics.Color
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import com.quanda.moviedb.data.model.Movie
import com.quanda.moviedb.databinding.FragmentBaseLoadmoreRefreshBinding
import com.quanda.moviedb.ui.base.fragment.BaseDataLoadMoreRefreshFragment
import com.quanda.moviedb.ui.screen.main.popularmovie.PopularMovieAdapter

class FavoriteMovieFragment : BaseDataLoadMoreRefreshFragment<FragmentBaseLoadmoreRefreshBinding, FavoriteMovieViewModel, Movie>() {

    companion object {
        fun newInstance(): FavoriteMovieFragment {
            return FavoriteMovieFragment()
        }
    }

    override fun initViewModel(): FavoriteMovieViewModel {
        return ViewModelProviders.of(this).get(FavoriteMovieViewModel::class.java)
                .apply {
                    navigator = this@FavoriteMovieFragment.navigator as FavoriteMovieNavigator
                }
    }

    override fun initData() {
        super.initData()
        binding.apply {
            root.setBackgroundColor(Color.BLACK)
            view = this@FavoriteMovieFragment
            viewModel = this@FavoriteMovieFragment.viewModel
            recyclerView.adapter.value = adapter
            recyclerView.layoutManager.value = layoutManager
        }

        if (viewModel.listItem.isEmpty()) {
            loadData()
        }
    }

    fun loadData() {
        viewModel.apply {
            isDataLoading.value = true
            loadData(1)
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        loadData()
    }

    override fun initAdapter(): RecyclerView.Adapter<RecyclerView.ViewHolder> {
        return PopularMovieAdapter(context!!, viewModel.listItem,
                viewModel.itemCLickListener) as RecyclerView.Adapter<RecyclerView.ViewHolder>
    }

    override fun initLayoutManager() = GridLayoutManager(context, 2)
}