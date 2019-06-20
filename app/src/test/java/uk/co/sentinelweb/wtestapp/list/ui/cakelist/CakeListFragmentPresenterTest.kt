package uk.co.sentinelweb.wtestapp.list.ui.cakelist

import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import uk.co.sentinelweb.wtestapp.LogWrapper
import uk.co.sentinelweb.wtestapp.domain.Cake
import uk.co.sentinelweb.wtestapp.net.service.CakeListService

@Suppress("EXPERIMENTAL_API_USAGE")
class CakeListFragmentPresenterTest {
    @Mock  lateinit var mockView:CakeListContract.View
    @Mock  lateinit var mockService:CakeListService
    @Mock  lateinit var mockLog:LogWrapper

    private val realViewModel:CakeListViewModel = CakeListViewModel()

    private lateinit var sut:CakeListFragmentPresenter
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        MockitoAnnotations.initMocks(this)

        sut = CakeListFragmentPresenter(mockView, mockService, mockLog)

    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun loadData_success() {
        runBlocking {
            whenever(mockService.listCakes()).thenReturn(listOf(Cake("x","y","z")))
            whenever(mockView.getCakeListViewModel()).thenReturn(realViewModel)

            sut.loadData(true)

            // TODO wait for result somehow?

            verify(mockView).showRefreshing(true)
            // TODO verify other invocations as per success/fail tests below
        }
    }

    @Test
    fun onLoadSuccess() {
        val testData = listOf(
            Cake("x", "y", "z"),
            Cake("a", "b", "c"),
            Cake("x", "y", "z")// duplicate

            )
        runBlocking {
            whenever(mockView.getCakeListViewModel()).thenReturn(realViewModel)

            sut.updateAfterLoadSuccess(testData)

            verify(mockView).updateDisplay()
            verify(mockView).showError(false)
            verify(mockView).showRefreshing(false)
            assertEquals(2, realViewModel.cakeList.size) // duplicate removed
            assertEquals("a", realViewModel.cakeList.get(0).title) // sorted by title
        }
    }

    @Test
    fun onLoadFail() {
        runBlocking {
            whenever(mockView.getCakeListViewModel()).thenReturn(realViewModel)

            sut.updateAfterLoadFail()

            verify(mockView).updateDisplay()
            verify(mockView).showError(true)
            verify(mockView).showRefreshing(false)
            assertEquals(0, mockView.getCakeListViewModel().cakeList.size)
        }
    }
}