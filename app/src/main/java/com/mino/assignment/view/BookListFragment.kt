package com.mino.assignment.view

import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.mino.assignment.*
import com.mino.assignment.basic.BaseFragment
import com.mino.assignment.data.model.DocumentModel
import com.mino.assignment.databinding.FragmentBookListBinding
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

    private fun showDetailFragment(documentModel: DocumentModel) {
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
        receiveEvent()
        viewLifecycleOwnerLiveData.observe(this, ::observeLiveData)

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

    private fun observeLiveData(viewLifecycleOwner: LifecycleOwner) {
        viewModel.run {
            query.observe(viewLifecycleOwner, EventObserver {
                scrollListener.clear()
                viewModel.getBook(it)
            })
            metaModel.observe(viewLifecycleOwner, EventObserver{
                scrollListener.searchBoolean = !it.isEnd
            })
            documentModelList.observe(viewLifecycleOwner, EventObserver{
                bookListAdapter.setItemsDiff(it.first,it.second)
                scrollListener.loading = false
            })
        }
    }

    private fun setScroll(linearLayoutManager: LinearLayoutManager) {
        scrollListener =
            EndlessScrollListener(linearLayoutManager) { page ->
                viewModel.query.value?.peekContent()?.let {
                    viewModel.getBook(page = page,query = it)
                }
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