package uk.co.sentinelweb.wtestapp.net.client

import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import uk.co.sentinelweb.wtestapp.net.client.RetrofitFactory.Companion.BASE_URL
import java.lang.RuntimeException

class RetrofitFactoryTest {

    private lateinit var sut: RetrofitFactory

    @Before
    fun setUp() {
        sut = RetrofitFactory()
    }

    @Test
    fun createClient() {
        val createClient = sut.createClient()
        assertNotNull(createClient)
        assertEquals(createClient.baseUrl().toString(), BASE_URL)
    }

    @Test
    fun createCakeListService() {
        val createCakeListService = sut.createCakeListService(sut.createClient())
        runBlocking {
            val listCakes = createCakeListService.listCakes()

            assertNotNull(listCakes)
            assertTrue("should be size = 20", listCakes.size == 20)
        }
    }
}