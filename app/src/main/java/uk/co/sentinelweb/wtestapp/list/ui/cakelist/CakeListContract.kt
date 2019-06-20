package uk.co.sentinelweb.wtestapp.list.ui.cakelist

interface CakeListContract {

    interface View {
        fun updateDisplay()
        fun getCakeListViewModel() : CakeListViewModel
        fun showError(show:Boolean)
        fun showRefreshing(show:Boolean)
    }

    interface Presenter {
        fun loadData(forceRefresh:Boolean = false)
        fun releaseJob()
    }
}