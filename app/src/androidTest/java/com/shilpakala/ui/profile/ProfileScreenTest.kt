package com.shilpakala.ui.profile

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.shilpakala.data.local.ArtisanProfileDao
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

@RunWith(AndroidJUnit4::class)
class ProfileScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun profileScreen_initialState_displaysRequiredFields() {
        // Mock the DAO to return an empty flow to avoid NPE in ViewModel init
        val mockDao = mock<ArtisanProfileDao> {
            on { getAll() } doReturn flowOf(emptyList())
        }
        val viewModel = ProfileViewModel(mockDao)

        composeTestRule.setContent {
            ProfileScreen(viewModel = viewModel)
        }

        // Check if "Artisan Name" text field is displayed (via label)
        composeTestRule.onNodeWithText("Artisan Name").assertIsDisplayed()
        
        // Check if "Save Profile" button is displayed
        composeTestRule.onNodeWithText("Save Profile").assertIsDisplayed()
    }

    @Test
    fun clickingSave_withEmptyName_showsValidationError() {
        val mockDao = mock<ArtisanProfileDao> {
            on { getAll() } doReturn flowOf(emptyList())
        }
        val viewModel = ProfileViewModel(mockDao)

        composeTestRule.setContent {
            ProfileScreen(viewModel = viewModel)
        }

        // Click "Save Profile" without entering any name
        composeTestRule.onNodeWithText("Save Profile").performClick()

        // Check if validation error text appears
        composeTestRule.onNodeWithText("Name is required").assertIsDisplayed()
        composeTestRule.onNodeWithText("Village is required").assertIsDisplayed()
    }
}
