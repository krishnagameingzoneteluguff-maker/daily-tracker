package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.VayuViewModel
import com.example.ui.screens.AssistantScreen
import com.example.ui.screens.DashboardScreen
import com.example.ui.screens.FitnessScreen
import com.example.ui.screens.FocusScreen
import com.example.ui.screens.PlannerScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.theme.*

class MainActivity : ComponentActivity() {
    private val viewModel: VayuViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                MainContent(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun MainContent(viewModel: VayuViewModel) {
    val currentScreenIndex by viewModel.currentScreenIndex.collectAsState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .testTag("main_scaffold"),
        containerColor = CosmicSpaceBg,
        topBar = { HeaderBanner() },
        bottomBar = { BottomNavigationBar(currentScreenIndex = currentScreenIndex) { index -> viewModel.navigateToScreen(index) } }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            when (currentScreenIndex) {
                0 -> DashboardScreen(viewModel = viewModel)
                1 -> PlannerScreen(viewModel = viewModel)
                2 -> FocusScreen(viewModel = viewModel)
                3 -> FitnessScreen(viewModel = viewModel)
                4 -> AssistantScreen(viewModel = viewModel)
                5 -> ProfileScreen(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun HeaderBanner() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(CosmicSpaceBg)
            .padding(top = 48.dp, bottom = 12.dp, start = 20.dp, end = 20.dp)
            .testTag("app_header"),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "YOUR DISCIPLINE • OUR TECHNOLOGY",
            color = NeonPink,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.5.sp,
            fontFamily = FontFamily.SansSerif
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = "Vayu Tech",
            color = SoftWhite,
            fontSize = 30.sp,
            fontWeight = FontWeight.Black,
            letterSpacing = (-0.5).sp,
            fontFamily = FontFamily.SansSerif
        )
        Spacer(modifier = Modifier.height(12.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(GlowBorder.copy(alpha = 0.5f))
        )
    }
}

@Composable
fun BottomNavigationBar(
    currentScreenIndex: Int,
    onTabSelected: (Int) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(CosmicSpaceBg)
            .padding(start = 12.dp, end = 12.dp, bottom = 20.dp)
            .testTag("custom_nav_bar")
    ) {
        NavigationBar(
            containerColor = DarkNeutral,
            tonalElevation = 2.dp,
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .clip(RoundedCornerShape(24.dp))
                .border(1.dp, GlowBorder, RoundedCornerShape(24.dp))
        ) {
            val items = listOf(
                NavigationItemData("Home", Icons.Default.Home, 0),
                NavigationItemData("Planner", Icons.Default.DateRange, 1),
                NavigationItemData("Focus", Icons.Default.PlayArrow, 2),
                NavigationItemData("Fitness", Icons.Default.Star, 3),
                NavigationItemData("AI Bot", Icons.Default.Face, 4),
                NavigationItemData("Profile", Icons.Default.AccountCircle, 5)
            )

            items.forEach { item ->
                val isSelected = currentScreenIndex == item.index
                NavigationBarItem(
                    selected = isSelected,
                    onClick = { onTabSelected(item.index) },
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.label,
                            modifier = Modifier.size(20.dp),
                            tint = if (isSelected) Color(0xFF21005D) else CoolGrey
                        )
                    },
                    label = {
                        Text(
                            text = item.label,
                            fontSize = 9.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) Color(0xFF21005D) else CoolGrey
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF21005D),
                        unselectedIconColor = CoolGrey,
                        indicatorColor = Color(0xFFEADDFF)
                    ),
                    modifier = Modifier.testTag("nav_item_${item.index}")
                )
            }
        }
    }
}

data class NavigationItemData(
    val label: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val index: Int
)
