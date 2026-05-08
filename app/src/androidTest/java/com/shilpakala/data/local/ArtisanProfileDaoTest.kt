package com.shilpakala.data.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class ArtisanProfileDaoTest {

    private lateinit var db: ShilpaKalaDatabase
    private lateinit var dao: ArtisanProfileDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, ShilpaKalaDatabase::class.java).build()
        dao = db.artisanProfileDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetProfile() = runBlocking {
        val profile = ArtisanProfile(
            name = "Lohith",
            village = "Channapatna",
            craftType = "Lacquerware",
            logoUri = null
        )
        
        dao.insert(profile)
        
        val allProfiles = dao.getAll().first()
        assertEquals(1, allProfiles.size)
        assertEquals("Lohith", allProfiles[0].name)
    }
}
