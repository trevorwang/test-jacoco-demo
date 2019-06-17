package com.mings.fileprovidertest

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner


private const val fakeString = "FileProviderTest"

@RunWith(
    MockitoJUnitRunner::class
)

class UnitTestSample {
    @Mock
    private lateinit var mockContext: Context

    @Before
    fun setup() {
        `when`(mockContext.getString(R.string.app_name))
            .thenReturn(fakeString)
    }

    @Test
    fun readStringFromContext_LocalizedString() {
        val name = mockContext.getString(R.string.app_name)
        assertThat(name).isEqualTo(fakeString)
    }
}

@RunWith(AndroidJUnit4::class)
class AndroidXTest {
    private val context = ApplicationProvider.getApplicationContext<Context>()
    @Test
    fun readStringFromContext_LocalizedString() {
        val name = context.getString(R.string.app_name)
        assertThat(name).isEqualTo(fakeString)
        assertThat(name).isNotEmpty()
    }
}