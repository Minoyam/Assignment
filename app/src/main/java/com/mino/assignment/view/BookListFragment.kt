package com.mino.assignment.view

import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.LinearLayoutManager
import com.mino.assignment.EndlessScrollListener
import com.mino.assignment.R
import com.mino.assignment.RxEventBus
import com.mino.assignment.basic.BaseFragment
import com.mino.assignment.data.model.DocumentModel
import com.mino.assignment.databinding.FragmentBookListBinding
import com.mino.assignment.plusAssign
import com.mino.assignment.view.adapter.BookListAdapter
import com.mino.assignment.viewmodel.BookViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class BookListFragment : BaseFragment<FragmentBookListBinding>(R.layout.fragment_book_list) {

    private val bookListAdapter: BookListAdapter by lazy {
        BookListAdapter {
            showDetailFragment(it)
        }.apply {
            setHasStableIds(true)
        }
    }

    private fun showDetailFragment(documentModel: DocumentModel)
    {
        setFragmentResult(
            SEND_DETAIL, bundleOf(DOCUMENT_MODEL to documentModel)
        )
        startFragment(BookDetailFragment.newInstance())
    }

    private val viewModel by viewModel<BookViewModel>()

    private lateinit var scrollListener: EndlessScrollListener

    override fun init() {
        setScroll(LinearLayoutManager(requireContext()))
        initView()
        observeLiveData()
        receiveEvent()
    }

    private fun initView() {
        binding.run {
            val linearLayoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            setScroll(linearLayoutManager)
            rvContent.apply {
                adapter = bookListAdapter
                layoutManager = linearLayoutManager
                addOnScrollListener(scrollListener)
            }
            vm = viewModel
        }
    }

    private fun observeLiveData() {
        viewModel.run {
            query.observe(this@BookListFragment, {
                viewModel.setPageIndex(1)
                scrollListener.clear()
                viewModel.getBook()
            })
            metaModel.observe(this@BookListFragment, {
                scrollListener.searchBoolean = !it.isEnd
            })
        }
    }

    private fun setScroll(linearLayoutManager: LinearLayoutManager) {
        scrollListener =
            EndlessScrollListener(linearLayoutManager) { page ->
                viewModel.setPageIndex(page)
                viewModel.getBook(page = page)
            }
    }

    private fun receiveEvent() {
        with(binding) {
            compositeDisposable += RxEventBus.subject.subscribe { resultCode ->
                when (resultCode) {
                    RxEventBus.NO_DATA -> {
                        tvNoSearch.visibility = View.VISIBLE
                    }
                    RxEventBus.HAVE_DATA -> {
                        tvNoSearch.visibility = View.GONE
                    }
                }
            }
        }
    }

    companion object {
        const val SEND_DETAIL = "SEND_DETAIL"
        const val DOCUMENT_MODEL = "DOCUMENT_MODEL"
        fun newInstance() = BookListFragment()
    }
}