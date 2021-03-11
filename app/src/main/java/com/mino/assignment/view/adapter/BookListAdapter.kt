package com.mino.assignment.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mino.assignment.DiffDefault
import com.mino.assignment.R
import com.mino.assignment.data.model.DocumentModel
import com.mino.assignment.databinding.ItemBookListBinding

class BookListAdapter(private val onClickAction: (DocumentModel) -> Unit) :
    RecyclerView.Adapter<BookListAdapter.BookListViewHolder>() {

    private val documentModelList = mutableListOf<DocumentModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookListViewHolder {
        val binding = DataBindingUtil.inflate<ItemBookListBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_book_list,
            parent,
            false
        )
        return BookListViewHolder(binding)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount(): Int = documentModelList.size

    override fun onBindViewHolder(holder: BookListViewHolder, position: Int) {
        holder.bind(documentModelList[position])
    }

    fun setItemsDiff(page: Int, items: List<DocumentModel>) {

        if(page == 1) {
            calDiff(items)
            documentModelList.clear()
            documentModelList.addAll(items)
        }
        else{
            val updateList = mutableListOf<DocumentModel>().apply {
                addAll(documentModelList)
            }
            updateList.addAll(items)
            calDiff(updateList)
            documentModelList.clear()
            documentModelList.addAll(updateList)
        }
    }
    private fun calDiff(updateList: List<DocumentModel>) {
        val diff = DiffDefault(documentModelList, updateList)
        val diffResult = DiffUtil.calculateDiff(diff)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class BookListViewHolder(private val binding: ItemBookListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onClickAction(documentModelList[adapterPosition])
            }
        }
        fun bind(item: DocumentModel) {
            binding.run {
                this.item = item
            }

        }
    }
}