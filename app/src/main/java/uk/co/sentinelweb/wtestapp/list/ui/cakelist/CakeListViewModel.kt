package uk.co.sentinelweb.wtestapp.list.ui.cakelist

import androidx.lifecycle.ViewModel
import uk.co.sentinelweb.wtestapp.domain.Cake

class CakeListViewModel : ViewModel() {
    var cakeList: List<Cake> = emptyList()
}
