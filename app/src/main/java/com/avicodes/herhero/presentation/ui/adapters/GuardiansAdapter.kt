package com.avicodes.herhero.presentation.ui.adapters

import android.app.Application
import android.view.LayoutInflater
import android.view.OnReceiveContentListener
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.graphics.Color
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.avicodes.herhero.R
import com.avicodes.herhero.data.models.Guardians
import com.avicodes.herhero.databinding.ItemGuardianBinding
import dagger.hilt.android.AndroidEntryPoint

class GuardiansAdapter(
    private val application: Application,
): RecyclerView.Adapter<GuardiansAdapter.GuardianViewHolder>() {

    private var callback = object : DiffUtil.ItemCallback<Guardians> (){
        override fun areItemsTheSame(oldItem: Guardians, newitem: Guardians): Boolean {
            return oldItem.id == newitem.id
        }

        override fun areContentsTheSame(oldItem: Guardians, newItem: Guardians): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, callback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuardianViewHolder {
        val binding = ItemGuardianBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GuardianViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: GuardianViewHolder, position: Int) {
        val guardians = differ.currentList[position]
        holder.bind(guardians)
    }

    inner class GuardianViewHolder(private val binding: ItemGuardianBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(guardians: Guardians) {
            binding.apply {
                tvName.text = guardians.name
                tvGid.text = guardians.phone
                if(guardians.isSuperGuard) {
                    superGuardView.visibility = View.VISIBLE
                    guardianView.setCardBackgroundColor(application.getColor(R.color.main1))
                }

                deleteBtn.setOnClickListener {
                    onItemClickListener?.let {
                        it(guardians)
                    }
                }

            }
        }
    }

    private var onItemClickListener: ((Guardians) -> Unit)?= null
    fun setOnItemClickListener(listener: (Guardians) -> Unit) {
        onItemClickListener = listener
    }

}