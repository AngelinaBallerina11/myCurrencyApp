package com.angelinaandronova.mycurrencyapp

import com.angelinaandronova.mycurrencyapp.network.rates.service.RatesService
import org.junit.Test

import org.junit.Assert.*
import com.angelinaandronova.mycurrencyapp.ui.main.MainRepository
import org.junit.Rule
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule



/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Rule
    var mockitoRule = MockitoJUnit.rule()
    @Mock
    var service: RatesService? = null
    @InjectMocks
    var repository: MainRepository? = null

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun repoTest() {
        when(service?.getRates()).thenReturn()
    }
}
