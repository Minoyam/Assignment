package com.mino.assignment.view

import androidx.fragment.app.setFragmentResultListener
import com.mino.assignment.R
import com.mino.assignment.basic.BaseFragment
import com.mino.assignment.databinding.FragmentBookDetailBinding
import com.mino.assignment.view.adapter.setLikeImage

class BookDetailFragment : BaseFragment<FragmentBookDetailBinding>(R.layout.fragment_book_detail) {


    /*
        툴바를 사용하지 않은 이유
        뒤로가기 버튼을 만들때 activity였다면 setSupportActionBar()를 사용해서 만들었을텐데
        공식 문서에서 fragment에서 사용하는 것을 권장하지 않아 toolBar를 사용하지 않았다.

     */
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