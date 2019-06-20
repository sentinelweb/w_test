package uk.co.sentinelweb.wtestapp.list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import uk.co.sentinelweb.wtestapp.R
import uk.co.sentinelweb.wtestapp.list.ui.cakelist.CakeListFragment

class CakeListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cake_list_activity)
        setTitle(getString(R.string.title_cake_list))
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, CakeListFragment.newInstance())
                .commitNow()
        }
    }

}
