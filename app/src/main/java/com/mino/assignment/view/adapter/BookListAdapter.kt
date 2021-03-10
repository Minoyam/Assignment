package com.mino.assignment.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mino.assignment.R
import com.mino.assignment.data.model.DocumentModel
import com.mino.assignment.databinding.ItemBookListBinding

class BookListAdapter(private val onClickAction: (DocumentModel) -> Unit) :
    RecyclerView.Adapter<BookListAdapter.BookListViewHolder>() {

    private val documentModelList = mutableListOf<DocumentModel>()

    fun setDocumentModel(list: List<DocumentModel>) {
        documentModelList.clear()
        documentModelList.addAll(list)
        notifyDataSetChanged()
        Log.e("setDocumentModel", documentModelList.size.toString())

    }

    fun setPagingDocumentModel(list: List<DocumentModel>) {
        list.filter { documentModel ->
            !documentModelList.contains(documentModel)
        }.apply {
            documentModelList.addAll(this)
        }
        notifyDataSetChanged()
        Log.e("setPagingDocumentModel", documentModelList.size.toString())
    }

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
                ivFavorite.setOnClickListener {
                    if (item.like) {
                        ivFavorite.setImageResource(R.drawable.ic_favorite_border_24dp)
                        documentModelList[adapterPosition].like = false
                    } else {
                        ivFavorite.setImageResource(R.drawable.ic_favorite_24dp)
                        documentModelList[adapterPosition].like = true
                    }
                }
            }

        }
    }

}