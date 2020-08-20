package com.example.mainactivity

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mainactivity.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.item_comment.view.*
import com.example.mainactivity.Comment as Comment


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val data = arrayListOf<Comment>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = CommentAdapter(data,
            onClickDeleteIcon = { it:Comment
            deleteComment(it)
         }
        binding.addButton.setonClickListner {it: View!
             addComment()
         }
    }
    private fun addComment() {
        val comment = Comment(binding.editText.text.toString())
        data.add(comment)
        binding.recyclerView.adapter?.notifyDataSetChanged()
    }
    private fun deleteComment(comment: Comment) {
        data.remove(comment)
        binding.recyclerView.adapter?.notifyDataSetChanged()
    }
}

data class Comment (val text:String)
class CommentAdapter(private val myDataset: List<Comment>, val onClickDeleteIcon: (comment: Comment) ->Unit ) :
    RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

    class CommentViewHolder(val binding: itemcommentBinding ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): CommentAdapter.CommentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_comment, parent, false)
        return CommentViewHolder(itemcommentBinding.bind(view))
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val comment = myDataset[position]
        holder.binding.comment_text.text = comment.text
        holder.binding.delete_imageView.setonClickerListner{
            onClickDeleteIcon.invoke(comment)
        }
    }

    override fun getItemCount() = myDataset.size
}
