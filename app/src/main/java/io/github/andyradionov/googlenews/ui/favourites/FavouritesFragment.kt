package io.github.andyradionov.googlenews.ui.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import io.github.andyradionov.googlenews.R
import io.github.andyradionov.googlenews.data.entities.Article
import io.github.andyradionov.googlenews.ui.common.BaseFragment
import javax.inject.Inject

class FavouritesFragment : BaseFragment(), FavouritesView {

    @Inject
    @InjectPresenter
    lateinit var presenter: FavouritesPresenter

    @ProvidePresenter
    fun providePresenter(): FavouritesPresenter = presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favourites, container, false)
    }

    override fun onResume() {
        super.onResume()
        presenter.loadFavourites()
    }

    override fun showNews(articles: List<Article>) {

    }

    override fun showError() {

    }

    override fun showLoading() {

    }

    override fun onFavouriteRemove(position: Int) {

    }
}
