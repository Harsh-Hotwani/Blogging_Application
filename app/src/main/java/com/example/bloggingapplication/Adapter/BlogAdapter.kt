package com.example.bloggingapplication.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bloggingapplication.ReadMoreActivity
import com.example.bloggingapplication.databinding.ActivityRegistrationBinding
import com.example.bloggingapplication.databinding.ItemUserBinding
import com.example.bloggingapplication.model.BlogItemModel

class BlogAdapter(private val items:List<BlogItemModel>): RecyclerView.Adapter<BlogAdapter.BlogViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlogViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemUserBinding.inflate(inflater,parent,false)
        return BlogViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BlogViewHolder, position: Int) {
        val blogitem = items[position]
        holder.bind(blogitem)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class BlogViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root){
        fun bind(blogItemModel: BlogItemModel) {
            binding.heading.text=blogItemModel.heading
            Glide.with(binding.profile.context)
                .load(blogItemModel.profileImage)
                .into(binding.profile)
            binding.userName.text=blogItemModel.userName
            binding.date.text=blogItemModel.date
            binding.post.text=blogItemModel.post
            binding.likeCount.text=blogItemModel.likeCount.toString()

            binding.root.setOnClickListener{
                val context = binding.root.context
                val intent = Intent(context,ReadMoreActivity::class.java)
                intent.putExtra("blogItem",blogItemModel)
                context.startActivity(intent)
            }
        }
    }
}