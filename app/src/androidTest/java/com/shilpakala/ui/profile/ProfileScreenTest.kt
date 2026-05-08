package com.shilpakala.ui.profile

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.shilpakala.data.local.ArtisanProfileDao
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock

class ProfileScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun profileScreen_initialState_displaysRequiredFields() {
        // Mock the DAO and ViewModel
        val mockDao = mock<ArtisanProfileDao>()
        // We need to provide a way to handle the Flow in ViewModel if we use a real one
        // For a UI test focusing on display/validation, we can use a fake/mock ViewModel if possible
        // but since ProfileViewModel is a class, let's use a real one with mock dependencies.
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
        val mockDao = mock<ArtisanProfileDao>()
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
