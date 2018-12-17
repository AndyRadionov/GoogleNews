package io.github.andyradionov.googlenews.ui.dialogs

import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.BottomSheetDialog
import android.support.design.widget.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.andyradionov.googlenews.R
import io.github.andyradionov.googlenews.data.entities.Article

/**
 * @author Andrey Radionov
 */

private const val ARG_ARTICLE = "article"

class NewsBottomSheetDialog : BottomSheetDialogFragment() {

    private lateinit var article: Article
    private var isFavourite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            article = it.getParcelable(ARG_ARTICLE) as Article
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        dialog.setOnShowListener { dialog ->
            val d = dialog as BottomSheetDialog
            val bottomSheet = d
                    .findViewById<View>(android.support.design.R.id.design_bottom_sheet)
            bottomSheet?.let {
                BottomSheetBehavior
                        .from(bottomSheet)
                        .state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        return inflater.inflate(R.layout.fragment_dialog_bottom, container, false)
    }

    override fun getTheme(): Int {
        return R.style.AppBottomSheetDialogTheme
    }

    companion object {
        val TAG: String = NewsBottomSheetDialog::class.java.simpleName

        fun newInstance(article: Article) =
                NewsBottomSheetDialog().apply {
                    arguments = Bundle().apply {
                        putParcelable(ARG_ARTICLE, article)
                    }
                }
    }
}
