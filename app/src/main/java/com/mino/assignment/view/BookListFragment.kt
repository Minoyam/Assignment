package com.mino.assignment.view

import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.mino.assignment.utils.EndlessScrollListener
import com.mino.assignment.utils.EventObserver
import com.mino.assignment.R
import com.mino.assignment.basic.BaseFragment
import com.mino.assignment.data.model.DocumentModel
import com.mino.assignment.databinding.FragmentBookListBinding
import com.mino.assignment.view.adapter.BookListAdapter
import com.mino.assignment.viewmodel.BookViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class BookListFragment : BaseFragment<FragmentBookListBinding>(R.layout.fragment_book_list) {

    private val viewModel by viewModel<BookViewModel>()

    private lateinit var scrollListener: EndlessScrollListener

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

    override fun init() {
        initView()
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
            query.observe(viewLifecycleOwner, EventObserver { query ->
                scrollListener.clear()
                viewModel.getBook(query = query)
            })
            metaModel.observe(viewLifecycleOwner, EventObserver { metaModel ->
                scrollListener.searchBoolean = !metaModel.isEnd
            })

            documentModelList.observe(
                viewLifecycleOwner,
                EventObserver { pageAndDocumentModelList ->
                    bookListAdapter.setItemsDiff(
                        page = pageAndDocumentModelList.first,
                        items = pageAndDocumentModelList.second
                    )
                    scrollListener.loading = false
                })

            errorMessage.observe(viewLifecycleOwner, EventObserver { errorMessage ->
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
            })

            isData.observe(viewLifecycleOwner, EventObserver { isData ->
                binding.tvNoSearch.isVisible = isData
            })
        }
    }

    private fun setScroll(linearLayoutManager: LinearLayoutManager) {
        scrollListener =
            EndlessScrollListener(linearLayoutManager) { page ->
                viewModel.query.value?.peekContent()?.let { query ->
                    viewModel.getBook(page = page, query = query)
                }
            }
    }
    companion object {
        const val SEND_DETAIL = "SEND_DETAIL"
        const val DOCUMENT_MODEL = "DOCUMENT_MODEL"
        fun newInstance() = BookListFragment()
    }
}