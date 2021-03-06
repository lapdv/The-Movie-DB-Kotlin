package com.quanda.moviedb.ui.screen.tv

import com.quanda.moviedb.App
import com.quanda.moviedb.data.constants.ApiParam
import com.quanda.moviedb.data.model.Tv
import com.quanda.moviedb.data.repository.impl.MovieRepository
import com.quanda.moviedb.ui.base.viewmodel.BaseDataLoadMoreRefreshViewModel
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import javax.inject.Inject

class TvListViewModel() : BaseDataLoadMoreRefreshViewModel<Tv>() {

    @Inject
    lateinit var movieRepository: MovieRepository

    lateinit var navigator: TvListNavigator

    init {
        App.appComponent.inject(this)
    }

    override fun loadData(page: Int) {
        val hashMap = HashMap<String, String>()
        hashMap.put(ApiParam.PAGE, page.toString())

        async(UI) {
            try {
                val response = movieRepository.getTvList(hashMap).await()
                currentPage = page
                if (currentPage == 1) listItem.clear()
                if (isRefreshing.value == true) resetLoadMore()
                listItem.addAll(response.results)
                onLoadSuccess(response)
            } catch (e: Throwable) {
                onLoadFail(e)
            }
        }

        /*
        movieRepository.getTvList(hashMap, { response ->
            currentPage = page
            if (currentPage == 1) listItem.clear()
            if (isRefreshing.value == true) resetLoadMore()
            listItem.addAll(response.results)
            onLoadSuccess(response)
        }, { e ->
            onLoadFail(e)
        })
        */

        /*
        val result = movieRepository.getTvList2(hashMap)
        when (result) {
            is Result.Success<GetTvListResponse> -> {
                val response = result.data
                currentPage = page
                if (currentPage == 1) listItem.clear()
                if (isRefreshing.value == true) resetLoadMore()
                listItem.addAll(response.results)
                onLoadSuccess(response)
            }
            is Result.Error -> {
                onLoadFail(result.error)
            }
        }
        */
    }

}