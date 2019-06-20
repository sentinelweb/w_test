package uk.co.sentinelweb.wtestapp.list.ui.cakelist

import uk.co.sentinelweb.wtestapp.LogWrapper
import uk.co.sentinelweb.wtestapp.net.client.RetrofitFactory

/**
 * A component-like object to inject dependencies for cake list
 */
class CakeListComponent {

    companion object {
        fun cakeListPresenter(view: CakeListContract.View): CakeListContract.Presenter {
            val retrofitFactory = RetrofitFactory()
            return CakeListFragmentPresenter(
                view,
                retrofitFactory.createCakeListService(retrofitFactory.createClient()),
                LogWrapper()
            )
        }
    }
}