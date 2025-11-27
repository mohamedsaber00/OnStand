package com.eid.onstand.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eid.onstand.core.models.Backgrounds
import com.eid.onstand.core.models.DashboardLayout
import com.eid.onstand.core.models.DashboardTile
import com.eid.onstand.feature.dashboard.tiles.TileRenderers
import org.koin.compose.viewmodel.koinViewModel

/**
 * HomeScreen that displays the selected background with dashboard tiles.
 * This is the main dock mode screen.
 */
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewModel = koinViewModel(),
    onNavigateToDashboard: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    if (uiState.isLoading) {
        Box(modifier = modifier.fillMaxSize())
        return
    }

    Box(modifier = modifier.fillMaxSize()) {
        // Background layer
        val backgroundToRender = uiState.selectedBackground ?: Backgrounds.default
        backgroundToRender.Render(modifier = Modifier.fillMaxSize())

        // Dashboard tiles overlay
        DashboardTilesOverlay(
            layout = uiState.layout,
            tiles = uiState.tiles,
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        )

        // FAB to navigate to dashboard customization
        FloatingActionButton(
            onClick = onNavigateToDashboard,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Text(text = "⚙", fontSize = 20.sp)
        }
    }
}

/**
 * Renders dashboard tiles according to the selected layout.
 */
@Composable
private fun DashboardTilesOverlay(
    layout: DashboardLayout,
    tiles: List<DashboardTile>,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        when (layout) {
            DashboardLayout.SINGLE -> SingleLayout(tiles)
            DashboardLayout.TWO_HORIZONTAL -> TwoHorizontalLayout(tiles)
            DashboardLayout.TWO_VERTICAL -> TwoVerticalLayout(tiles)
            DashboardLayout.THREE_LEFT_DOMINANT -> ThreeLeftDominantLayout(tiles)
            DashboardLayout.THREE_RIGHT_DOMINANT -> ThreeRightDominantLayout(tiles)
            DashboardLayout.GRID_2X2 -> Grid2x2Layout(tiles)
        }
    }
}

@Composable
private fun SingleLayout(tiles: List<DashboardTile>) {
    Box(modifier = Modifier.fillMaxSize()) {
        tiles.getOrNull(0)?.let { tile ->
            RenderTile(tile = tile, modifier = Modifier.fillMaxSize())
        }
    }
}

@Composable
private fun TwoHorizontalLayout(tiles: List<DashboardTile>) {
    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        tiles.take(2).forEach { tile ->
            RenderTile(tile = tile, modifier = Modifier.weight(1f).fillMaxHeight())
        }
    }
}

@Composable
private fun TwoVerticalLayout(tiles: List<DashboardTile>) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        tiles.take(2).forEach { tile ->
            RenderTile(tile = tile, modifier = Modifier.weight(1f).fillMaxWidth())
        }
    }
}

@Composable
private fun ThreeLeftDominantLayout(tiles: List<DashboardTile>) {
    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        tiles.getOrNull(0)?.let { tile ->
            RenderTile(tile = tile, modifier = Modifier.weight(1f).fillMaxHeight())
        }

        Column(
            modifier = Modifier.weight(1f).fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            tiles.getOrNull(1)?.let { tile ->
                RenderTile(tile = tile, modifier = Modifier.weight(1f).fillMaxWidth())
            }
            tiles.getOrNull(2)?.let { tile ->
                RenderTile(tile = tile, modifier = Modifier.weight(1f).fillMaxWidth())
            }
        }
    }
}

@Composable
private fun ThreeRightDominantLayout(tiles: List<DashboardTile>) {
    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Column(
            modifier = Modifier.weight(1f).fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            tiles.getOrNull(0)?.let { tile ->
                RenderTile(tile = tile, modifier = Modifier.weight(1f).fillMaxWidth())
            }
            tiles.getOrNull(1)?.let { tile ->
                RenderTile(tile = tile, modifier = Modifier.weight(1f).fillMaxWidth())
            }
        }

        tiles.getOrNull(2)?.let { tile ->
            RenderTile(tile = tile, modifier = Modifier.weight(1f).fillMaxHeight())
        }
    }
}

@Composable
private fun Grid2x2Layout(tiles: List<DashboardTile>) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.weight(1f).fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            tiles.getOrNull(0)?.let { tile ->
                RenderTile(tile = tile, modifier = Modifier.weight(1f).fillMaxHeight())
            }
            tiles.getOrNull(1)?.let { tile ->
                RenderTile(tile = tile, modifier = Modifier.weight(1f).fillMaxHeight())
            }
        }

        Row(
            modifier = Modifier.weight(1f).fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            tiles.getOrNull(2)?.let { tile ->
                RenderTile(tile = tile, modifier = Modifier.weight(1f).fillMaxHeight())
            }
            tiles.getOrNull(3)?.let { tile ->
                RenderTile(tile = tile, modifier = Modifier.weight(1f).fillMaxHeight())
            }
        }
    }
}

@Composable
private fun RenderTile(
    tile: DashboardTile,
    modifier: Modifier = Modifier
) {
    val renderer = TileRenderers.getRenderer(tile.type)
    renderer.Render(
        tile = tile,
        isDragging = false,
        modifier = modifier
    )
}
