package com.eid.onstand.feature.dashboard.customization

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eid.onstand.core.models.DashboardLayout
import com.eid.onstand.core.models.DashboardTile

/**
 * Renders a preview of the dashboard layout with placeholder tiles.
 */
@Composable
fun LayoutPreview(
    layout: DashboardLayout,
    tiles: List<DashboardTile>,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
            .padding(8.dp)
    ) {
        when (layout) {
            DashboardLayout.SINGLE -> SingleLayoutPreview(tiles)
            DashboardLayout.TWO_HORIZONTAL -> TwoHorizontalLayoutPreview(tiles)
            DashboardLayout.TWO_VERTICAL -> TwoVerticalLayoutPreview(tiles)
            DashboardLayout.THREE_LEFT_DOMINANT -> ThreeLeftDominantLayoutPreview(tiles)
            DashboardLayout.THREE_RIGHT_DOMINANT -> ThreeRightDominantLayoutPreview(tiles)
            DashboardLayout.GRID_2X2 -> Grid2x2LayoutPreview(tiles)
        }
    }
}

@Composable
private fun SingleLayoutPreview(tiles: List<DashboardTile>) {
    Box(modifier = Modifier.fillMaxSize()) {
        tiles.getOrNull(0)?.let { tile ->
            TilePreviewBox(
                tile = tile,
                modifier = Modifier.fillMaxSize()
            )
        } ?: PlaceholderTile(modifier = Modifier.fillMaxSize())
    }
}

@Composable
private fun TwoHorizontalLayoutPreview(tiles: List<DashboardTile>) {
    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        repeat(2) { index ->
            tiles.getOrNull(index)?.let { tile ->
                TilePreviewBox(
                    tile = tile,
                    modifier = Modifier.weight(1f).fillMaxHeight()
                )
            } ?: PlaceholderTile(modifier = Modifier.weight(1f).fillMaxHeight())
        }
    }
}

@Composable
private fun TwoVerticalLayoutPreview(tiles: List<DashboardTile>) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        repeat(2) { index ->
            tiles.getOrNull(index)?.let { tile ->
                TilePreviewBox(
                    tile = tile,
                    modifier = Modifier.weight(1f).fillMaxWidth()
                )
            } ?: PlaceholderTile(modifier = Modifier.weight(1f).fillMaxWidth())
        }
    }
}

@Composable
private fun ThreeLeftDominantLayoutPreview(tiles: List<DashboardTile>) {
    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        // Large tile on left
        tiles.getOrNull(0)?.let { tile ->
            TilePreviewBox(
                tile = tile,
                modifier = Modifier.weight(1f).fillMaxHeight()
            )
        } ?: PlaceholderTile(modifier = Modifier.weight(1f).fillMaxHeight())

        // Two stacked tiles on right
        Column(
            modifier = Modifier.weight(1f).fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            tiles.getOrNull(1)?.let { tile ->
                TilePreviewBox(
                    tile = tile,
                    modifier = Modifier.weight(1f).fillMaxWidth()
                )
            } ?: PlaceholderTile(modifier = Modifier.weight(1f).fillMaxWidth())

            tiles.getOrNull(2)?.let { tile ->
                TilePreviewBox(
                    tile = tile,
                    modifier = Modifier.weight(1f).fillMaxWidth()
                )
            } ?: PlaceholderTile(modifier = Modifier.weight(1f).fillMaxWidth())
        }
    }
}

@Composable
private fun ThreeRightDominantLayoutPreview(tiles: List<DashboardTile>) {
    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        // Two stacked tiles on left
        Column(
            modifier = Modifier.weight(1f).fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            tiles.getOrNull(0)?.let { tile ->
                TilePreviewBox(
                    tile = tile,
                    modifier = Modifier.weight(1f).fillMaxWidth()
                )
            } ?: PlaceholderTile(modifier = Modifier.weight(1f).fillMaxWidth())

            tiles.getOrNull(1)?.let { tile ->
                TilePreviewBox(
                    tile = tile,
                    modifier = Modifier.weight(1f).fillMaxWidth()
                )
            } ?: PlaceholderTile(modifier = Modifier.weight(1f).fillMaxWidth())
        }

        // Large tile on right
        tiles.getOrNull(2)?.let { tile ->
            TilePreviewBox(
                tile = tile,
                modifier = Modifier.weight(1f).fillMaxHeight()
            )
        } ?: PlaceholderTile(modifier = Modifier.weight(1f).fillMaxHeight())
    }
}

@Composable
private fun Grid2x2LayoutPreview(tiles: List<DashboardTile>) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Row(
            modifier = Modifier.weight(1f).fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            tiles.getOrNull(0)?.let { tile ->
                TilePreviewBox(
                    tile = tile,
                    modifier = Modifier.weight(1f).fillMaxHeight()
                )
            } ?: PlaceholderTile(modifier = Modifier.weight(1f).fillMaxHeight())

            tiles.getOrNull(1)?.let { tile ->
                TilePreviewBox(
                    tile = tile,
                    modifier = Modifier.weight(1f).fillMaxHeight()
                )
            } ?: PlaceholderTile(modifier = Modifier.weight(1f).fillMaxHeight())
        }

        Row(
            modifier = Modifier.weight(1f).fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            tiles.getOrNull(2)?.let { tile ->
                TilePreviewBox(
                    tile = tile,
                    modifier = Modifier.weight(1f).fillMaxHeight()
                )
            } ?: PlaceholderTile(modifier = Modifier.weight(1f).fillMaxHeight())

            tiles.getOrNull(3)?.let { tile ->
                TilePreviewBox(
                    tile = tile,
                    modifier = Modifier.weight(1f).fillMaxHeight()
                )
            } ?: PlaceholderTile(modifier = Modifier.weight(1f).fillMaxHeight())
        }
    }
}

/**
 * Preview box for a single tile showing its type icon and title.
 */
@Composable
private fun TilePreviewBox(
    tile: DashboardTile,
    modifier: Modifier = Modifier
) {
    val backgroundColor = getTileColor(tile)

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = getTileIcon(tile),
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = tile.title,
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White,
                textAlign = TextAlign.Center,
                maxLines = 1
            )
        }
    }
}

@Composable
private fun PlaceholderTile(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "+",
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
        )
    }
}

private fun getTileColor(tile: DashboardTile): Color {
    return when (tile.type) {
        com.eid.onstand.core.models.TileType.CLOCK -> Color(0xFF6366F1) // Indigo
        com.eid.onstand.core.models.TileType.WEATHER -> Color(0xFF3B82F6) // Blue
        com.eid.onstand.core.models.TileType.TODO_LIST -> Color(0xFF22C55E) // Green
        com.eid.onstand.core.models.TileType.NOTES -> Color(0xFFF59E0B) // Amber
    }
}

private fun getTileIcon(tile: DashboardTile): String {
    return when (tile.type) {
        com.eid.onstand.core.models.TileType.CLOCK -> "🕐"
        com.eid.onstand.core.models.TileType.WEATHER -> "🌤️"
        com.eid.onstand.core.models.TileType.TODO_LIST -> "📋"
        com.eid.onstand.core.models.TileType.NOTES -> "📝"
    }
}

/**
 * Small thumbnail preview for layout selection.
 */
@Composable
fun LayoutThumbnail(
    layout: DashboardLayout,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(6.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(4.dp)
    ) {
        when (layout) {
            DashboardLayout.SINGLE -> ThumbnailSingle()
            DashboardLayout.TWO_HORIZONTAL -> ThumbnailTwoHorizontal()
            DashboardLayout.TWO_VERTICAL -> ThumbnailTwoVertical()
            DashboardLayout.THREE_LEFT_DOMINANT -> ThumbnailThreeLeft()
            DashboardLayout.THREE_RIGHT_DOMINANT -> ThumbnailThreeRight()
            DashboardLayout.GRID_2X2 -> ThumbnailGrid2x2()
        }
    }
}

@Composable
private fun ThumbnailSingle() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(3.dp))
            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.7f))
    )
}

@Composable
private fun ThumbnailTwoHorizontal() {
    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        repeat(2) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(3.dp))
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.7f))
            )
        }
    }
}

@Composable
private fun ThumbnailTwoVertical() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        repeat(2) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(3.dp))
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.7f))
            )
        }
    }
}

@Composable
private fun ThumbnailThreeLeft() {
    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .clip(RoundedCornerShape(3.dp))
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.7f))
        )
        Column(
            modifier = Modifier.weight(1f).fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            repeat(2) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(3.dp))
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.5f))
                )
            }
        }
    }
}

@Composable
private fun ThumbnailThreeRight() {
    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Column(
            modifier = Modifier.weight(1f).fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            repeat(2) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(3.dp))
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.5f))
                )
            }
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .clip(RoundedCornerShape(3.dp))
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.7f))
        )
    }
}

@Composable
private fun ThumbnailGrid2x2() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        repeat(2) { rowIndex ->
            Row(
                modifier = Modifier.weight(1f).fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                repeat(2) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(3.dp))
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.7f))
                    )
                }
            }
        }
    }
}
