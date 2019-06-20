package uk.co.sentinelweb.wtestapp.list.ui.cakelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import uk.co.sentinelweb.wtestapp.R


class CakeListFragment : Fragment(), CakeListContract.View {

    companion object {
        fun newInstance() = CakeListFragment()
    }

    private lateinit var viewModel: CakeListViewModel
    private lateinit var cakeListRoot: View
    private lateinit var cakeListRecycler: RecyclerView
    private lateinit var cakeListAdapter: CakeListRecyclerViewAdapter
    private lateinit var cakeListSwipe: SwipeRefreshLayout
    private lateinit var cakeListPresenter: CakeListContract.Presenter

    private var snackBar: Snackbar? = null

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
        cakeListRoot = inflate.findViewById(R.id.cake_list)
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

    override fun showError(show: Boolean) {
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

    override fun showRefreshing(show: Boolean) {
        cakeListSwipe.isRefreshing = show
    }

}
