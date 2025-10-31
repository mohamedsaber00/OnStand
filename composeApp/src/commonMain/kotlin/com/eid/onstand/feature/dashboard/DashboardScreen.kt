package com.eid.onstand.feature.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eid.onstand.core.ui.ReorderableGrid
import com.eid.onstand.core.ui.rememberReorderableGridState
import com.eid.onstand.feature.dashboard.tiles.TileRenderers
import org.koin.compose.viewmodel.koinViewModel

/**
 * Dashboard screen displaying a 2x2 grid of reorderable tiles.
 * Users can drag and drop tiles to reorder them.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit = {},
    viewModel: DashboardViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Dashboard") },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Text(
                            text = "←",
                            fontSize = 24.sp
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.resetToDefault() }) {
                        Text(
                            text = "↻",
                            fontSize = 24.sp
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                DashboardContent(
                    tiles = uiState.tiles,
                    onTilesReordered = viewModel::onTilesReordered
                )
            }
        }
    }
}

@Composable
private fun DashboardContent(
    tiles: List<com.eid.onstand.core.models.DashboardTile>,
    onTilesReordered: (List<com.eid.onstand.core.models.DashboardTile>) -> Unit
) {
    val gridState = rememberReorderableGridState(
        items = tiles,
        onReorder = onTilesReordered,
        key = { it.id }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Info text
        Text(
            text = "Drag tiles to reorder them",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Reorderable grid
        ReorderableGrid(
            items = tiles,
            state = gridState,
            modifier = Modifier.weight(1f),
            key = { it.id }
        ) { tile, index, isDragging ->
            val renderer = TileRenderers.getRenderer(tile.type)
            renderer.Render(
                tile = tile,
                isDragging = isDragging,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
