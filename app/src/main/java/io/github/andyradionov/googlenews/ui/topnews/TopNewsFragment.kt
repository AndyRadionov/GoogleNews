package io.github.andyradionov.googlenews.ui.topnews

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import io.github.andyradionov.googlenews.R
import io.github.andyradionov.googlenews.data.entities.Article
import io.github.andyradionov.googlenews.ui.common.BaseFragment
import io.github.andyradionov.googlenews.ui.common.BaseNewsView
import kotlinx.android.synthetic.main.news_content_layout.*
import javax.inject.Inject

class TopNewsFragment : BaseFragment(), BaseNewsView {

    @Inject
    @InjectPresenter
    lateinit var presenter: TopNewsPresenter

    @ProvidePresenter
    fun providePresenter(): TopNewsPresenter {
        return presenter
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.news_content_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpSwipeRefresh()
        setUpRecycler()
    }

    override fun onResume() {
        super.onResume()
        if (newsAdapter.itemCount == 0) {
            loadNews()
        }
    }

    override fun showNews(articles: List<Article>) {
        setVisibility(container = true)
        newsAdapter.items = articles
    }

    override fun showError() {
        setVisibility(empty = true)
    }

    override fun showLoading() {
        setVisibility(container = false, loading = true)
    }

    private fun setUpSwipeRefresh() {
        swipe_container.setOnRefreshListener {
            presenter.fetchNews()
        }
    }

    private fun setUpRecycler() {
        rv_news_container.adapter = newsAdapter

        val columnsNumber = resources.getInteger(R.integer.columns_number)
        val layoutManager = GridLayoutManager(activity, columnsNumber)

        rv_news_container.layoutManager = layoutManager
    }

    private fun setVisibility(container: Boolean = false,
                              loading: Boolean = false,
                              empty: Boolean = false) {

        rv_news_container.visibility = if (container) View.VISIBLE else View.INVISIBLE
        swipe_container.isRefreshing = loading
        tv_empty_view.visibility = if (empty) View.VISIBLE else View.INVISIBLE
    }

    private fun loadNews() {
        showLoading()
        presenter.fetchNews()
    }
}
