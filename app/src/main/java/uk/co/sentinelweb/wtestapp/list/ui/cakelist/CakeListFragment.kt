package uk.co.sentinelweb.wtestapp.list.ui.cakelist

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.item_list_content.view.*
import uk.co.sentinelweb.wtestapp.R
import uk.co.sentinelweb.wtestapp.domain.Cake
import androidx.recyclerview.widget.DividerItemDecoration
import com.squareup.picasso.Picasso



class CakeListFragment : Fragment(), CakeListContract.View {

    companion object {
        fun newInstance() = CakeListFragment()
    }

    private lateinit var viewModel: CakeListViewModel
    private lateinit var cakeListRoot: View
    private lateinit var cakeListRecycler: RecyclerView
    private lateinit var cakeListSwipe: SwipeRefreshLayout
    private lateinit var cakeListAdapter: CakeListRecyclerViewAdapter
    private lateinit var cakeListPresenter: CakeListContract.Presenter

    private var snackBar:Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cakeListPresenter = CakeListComponent.cakeListPresenter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val inflate = inflater.inflate(R.layout.cake_list_fragment, container, false)
        cakeListRecycler = inflate.findViewById(R.id.cake_list_recycler)
        cakeListSwipe = inflate.findViewById(R.id.cake_list_swipe)
        cakeListRoot = inflate.findViewById(R.id.cakelist)
        return inflate
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CakeListViewModel::class.java)
        setupRecyclerView(cakeListRecycler)
    }

    override fun onResume() {
        super.onResume()
        cakeListPresenter.loadData()
    }

    override fun onPause() {
        cakeListPresenter.releaseJob()
        super.onPause()
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        cakeListAdapter = CakeListRecyclerViewAdapter(
            viewModel.cakeList
        )
        recyclerView.adapter = cakeListAdapter
        cakeListSwipe.setOnRefreshListener {
            cakeListPresenter.loadData(true)
        }
        val dividerItemDecoration = DividerItemDecoration(
            recyclerView.context,
            DividerItemDecoration.VERTICAL
        )
        recyclerView.addItemDecoration(dividerItemDecoration)

    }

    override fun updateDisplay() {
        cakeListAdapter.items = viewModel.cakeList.toMutableList()
    }

    override fun getCakeListViewModel(): CakeListViewModel {
        return viewModel
    }

    override fun showError(show:Boolean) {
        snackBar?.dismiss()
        snackBar = null
        if (show) {
            snackBar = Snackbar.make(cakeListRoot, getString(R.string.error_loading), Snackbar.LENGTH_INDEFINITE)
            snackBar?.setAction(getString(R.string.action_retry), {
                cakeListPresenter.loadData(true)
            })
            snackBar?.show()
        }
    }

    override fun showRefreshing(show:Boolean) {
        cakeListSwipe.isRefreshing = show
    }


    class CakeListRecyclerViewAdapter(
        initialValues: List<Cake>
    ) : RecyclerView.Adapter<CakeListRecyclerViewAdapter.ViewHolder>() {

        var items = initialValues.toMutableList()
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
            val translateX = ObjectAnimator.ofFloat(
                holder.rootView, "translationX", -200f, 0f)
            translateX.start()
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
}
