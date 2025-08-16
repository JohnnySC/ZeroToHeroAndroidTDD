package ru.easycode.zerotoheroandroidtdd

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.easycode.zerotoheroandroidtdd.databinding.ItemLayoutBinding

class ItemAdapter : RecyclerView.Adapter<ItemViewHolder>() {

    private val itemList = ArrayList<CharSequence>()

    fun add(source: CharSequence) {
        itemList.add(source)
        notifyItemInserted(itemList.size - 1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(ItemLayoutBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun getItemCount() = itemList.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    fun save(bundle: Bundle) {
        bundle.putCharSequenceArrayList("key", itemList)
    }

    fun restore(bundle: Bundle) {
        itemList.addAll(bundle.getCharSequenceArrayList("key") ?: ArrayList())
        notifyItemRangeInserted(0, itemList.size)
    }

}

class ItemViewHolder(private val binding: ItemLayoutBinding) : ViewHolder(binding.root) {
    fun bind(source: CharSequence) {
        binding.elementTextView.text = source
    }
}