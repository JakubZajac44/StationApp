package com.jakub.zajac.station.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jakub.zajac.station.app.ui.theme.StationAppTheme
import com.jakub.zajac.station.stations.presentation.StationListScreen
import com.jakub.zajac.station.stations.presentation.StationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StationAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val viewModel: StationViewModel = hiltViewModel()
                    StationListScreen(
                        state = viewModel.state.collectAsStateWithLifecycle().value
                    )
                }
            }
        }
    }
}

