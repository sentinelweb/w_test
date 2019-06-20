package uk.co.sentinelweb.wtestapp.list.ui.cakelist

import android.animation.ObjectAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import uk.co.sentinelweb.wtestapp.R
import uk.co.sentinelweb.wtestapp.domain.Cake
import kotlinx.android.synthetic.main.item_list_content.view.*

// FIXME this should be a top level class for readability
class CakeListRecyclerViewAdapter(
        initialValues: List<Cake>
    ) : RecyclerView.Adapter<CakeListRecyclerViewAdapter.ViewHolder>() {

        internal var items = initialValues.toMutableList()
            set(cakeList) {
                field = cakeList
                notifyDataSetChanged()
            }

        private val onClickListener: View.OnClickListener

        init {
            onClickListener = View.OnClickListener { v ->
                val item = v.tag as Cake
                Toast.makeText(v.context, item.desc, Toast.LENGTH_SHORT).show()
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list_content, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = items[position]
            holder.titleView.text = item.title
            Picasso.get().load(item.image).into(holder.imageView)
            // FIXME
            ObjectAnimator.ofFloat(
                holder.rootView, "translationX", -200f, 0f
            ).start()

            with(holder.itemView) {
                tag = item
                setOnClickListener(onClickListener)
            }
        }

        override fun getItemCount() = items.size

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val rootView: View = view
            val titleView: TextView = view.item_title
            val imageView: ImageView = view.item_image
        }
    }