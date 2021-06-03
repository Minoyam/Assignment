package com.mino.assignment.view

import androidx.fragment.app.setFragmentResultListener
import com.mino.assignment.R
import com.mino.assignment.basic.BaseFragment
import com.mino.assignment.databinding.FragmentBookDetailBinding
import com.mino.assignment.view.adapter.setLikeImage

class BookDetailFragment : BaseFragment<FragmentBookDetailBinding>(R.layout.fragment_book_detail) {


    override fun init() {
        binding.run {
            setFragmentResultListener(BookListFragment.SEND_DETAIL) { _, bundle ->
                item = bundle.getParcelable(BookListFragment.DOCUMENT_MODEL)
            }
            ivFavorite.setOnClickListener {
                item?.apply {
                    this.like = !this.like
                    ivFavorite.setLikeImage(this.like)
                }
            }
            ivBack.setOnClickListener {
                onBackPressed()
            }
        }
    }

    companion object {
        fun newInstance() = BookDetailFragment()
    }
}