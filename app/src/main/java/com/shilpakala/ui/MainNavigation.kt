package com.shilpakala.ui

import android.net.Uri
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.NavHostController
import androidx.navigation.navArgument
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.shilpakala.ui.branding.BrandingScreen
import com.shilpakala.ui.camera.CameraScreen
import com.shilpakala.ui.gallery.GalleryScreen
import com.shilpakala.ui.profile.ProfileScreen

@Composable
fun MainNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues()
) {
    NavHost(
        navController = navController,
        startDestination = "profile",
        modifier = modifier.padding(contentPadding)
    ) {
        composable("profile") { ProfileScreen() }
        composable("camera") { CameraScreen(navController = navController) }
        composable(
            route = "branding/{imageUri}",
            arguments = listOf(navArgument("imageUri") { type = NavType.StringType })
        ) { backStackEntry ->
            BrandingScreen(
                imageUri = Uri.decode(backStackEntry.arguments?.getString("imageUri").orEmpty()),
                navController = navController
            )
        }
        composable("gallery") { GalleryScreen(navController = navController) }
    }
}
