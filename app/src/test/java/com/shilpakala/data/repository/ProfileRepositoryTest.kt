package com.shilpakala.data.repository

import com.shilpakala.data.local.ArtisanProfile
import org.junit.Assert.assertEquals
import org.junit.Test

class ProfileRepositoryTest {

    @Test
    fun artisanProfile_holdsCorrectValuesAfterCreation() {
        // Arrange
        val name = "Lohith"
        val village = "Channapatna"
        val craftType = "Lacquerware"
        val logoUri = "content://media/external/images/media/1"

        // Act
        val profile = ArtisanProfile(
            name = name,
            village = village,
            craftType = craftType,
            logoUri = logoUri
        )

        // Assert
        assertEquals(name, profile.name)
        assertEquals(village, profile.village)
        assertEquals(craftType, profile.craftType)
        assertEquals(logoUri, profile.logoUri)
    }
}
