package uk.co.sentinelweb.wtestapp.list.ui.cakelist

import androidx.annotation.VisibleForTesting
import kotlinx.coroutines.*
import uk.co.sentinelweb.wtestapp.LogWrapper
import uk.co.sentinelweb.wtestapp.domain.Cake
import uk.co.sentinelweb.wtestapp.net.service.CakeListService
import java.io.IOException

class CakeListFragmentPresenter constructor(
    private val view: CakeListContract.View,
    private val service: CakeListService,
    private val log: LogWrapper
) : CakeListContract.Presenter {

    companion object {
        private val LOG_TAG = CakeListFragmentPresenter::class.java.simpleName
    }

    private lateinit var loadJob: Job

    // todo : instead of just executing here i would normally make an interactor class to handle the api execution
    override fun loadData(forceRefresh: Boolean) {
        if (view.getCakeListViewModel().cakeList.isEmpty() || forceRefresh) {
            view.showRefreshing(true)
            loadJob = CoroutineScope(Dispatchers.IO).launch {
                try {
                    log.d(LOG_TAG, "Loading data ...", null)
                    val result = service.listCakes()

                    updateAfterLoadSuccess(result)
                } catch (ioException: IOException) {
                    withContext(Dispatchers.Main) {updateAfterLoadFail()}
                    log.d(LOG_TAG, "Error loading data", ioException)
                }

            }
        }
    }

    /**
     * cancels the load job if currently active
     */
    override fun releaseJob() {
        if (::loadJob.isInitialized && loadJob.isActive) {
            loadJob.cancel()
        }
    }

    @VisibleForTesting suspend fun updateAfterLoadSuccess(result: List<Cake>) =
        withContext(Dispatchers.Main) {
            view.getCakeListViewModel().cakeList = result.distinct().sortedBy { it.title }
            view.updateDisplay()
            view.showError(false)
            view.showRefreshing(false)
        }

    @VisibleForTesting suspend fun updateAfterLoadFail() =
        withContext(Dispatchers.Main) {
            view.getCakeListViewModel().cakeList = emptyList()
            view.updateDisplay()
            view.showError(true)
            view.showRefreshing(false)
        }

}