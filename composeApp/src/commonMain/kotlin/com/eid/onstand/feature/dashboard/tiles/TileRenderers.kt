package com.eid.onstand.feature.dashboard.tiles

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eid.onstand.core.models.DashboardTile
import com.eid.onstand.core.models.TileRenderer
import com.eid.onstand.core.models.TileType

/**
 * Provides tile renderers for all tile types.
 */
object TileRenderers {

    private val renderers = mapOf<TileType, TileRenderer>(
        TileType.CLOCK to ClockTileRenderer,
        TileType.WEATHER to WeatherTileRenderer,
        TileType.TODO_LIST to TodoTileRenderer,
        TileType.NOTES to NotesTileRenderer
    )

    fun getRenderer(type: TileType): TileRenderer {
        return renderers[type] ?: EmptyTileRenderer
    }
}

/**
 * Base composable for tile card styling.
 */
@Composable
private fun TileCard(
    title: String,
    iconText: String,
    isDragging: Boolean,
    backgroundColor: Color,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxSize()
            .shadow(
                elevation = if (isDragging) 8.dp else 2.dp,
                shape = RoundedCornerShape(16.dp)
            )
            .alpha(if (isDragging) 0.8f else 1f),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Header with icon and title
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = iconText,
                    fontSize = 24.sp,
                    color = Color.White.copy(alpha = 0.7f)
                )
            }

            // Content
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                content()
            }
        }
    }
}

/**
 * Clock tile renderer.
 */
object ClockTileRenderer : TileRenderer {
    @Composable
    override fun Render(tile: DashboardTile, isDragging: Boolean, modifier: Modifier) {
        TileCard(
            title = tile.title,
            iconText = "🕐",
            isDragging = isDragging,
            backgroundColor = Color(0xFF6366F1),
            modifier = modifier
        ) {
            // Static clock display (placeholder)
            Text(
                text = "12:00",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Mon, Jan 1",
                fontSize = 14.sp,
                color = Color.White.copy(alpha = 0.8f),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

/**
 * Weather tile renderer.
 */
object WeatherTileRenderer : TileRenderer {
    @Composable
    override fun Render(tile: DashboardTile, isDragging: Boolean, modifier: Modifier) {
        TileCard(
            title = tile.title,
            iconText = "☁️",
            isDragging = isDragging,
            backgroundColor = Color(0xFF0EA5E9),
            modifier = modifier
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "24°C",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "☁️ Partly Cloudy",
                    fontSize = 14.sp,
                    color = Color.White.copy(alpha = 0.9f)
                )
            }
        }
    }
}

/**
 * Todo list tile renderer.
 */
object TodoTileRenderer : TileRenderer {
    @Composable
    override fun Render(tile: DashboardTile, isDragging: Boolean, modifier: Modifier) {
        TileCard(
            title = tile.title,
            iconText = "✓",
            isDragging = isDragging,
            backgroundColor = Color(0xFF10B981),
            modifier = modifier
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TodoItem("Complete project", true)
                TodoItem("Review code", false)
                TodoItem("Team meeting", false)
            }
        }
    }

    @Composable
    private fun TodoItem(text: String, completed: Boolean) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = if (completed) "✓" else "○",
                fontSize = 16.sp,
                color = if (completed) Color.White else Color.White.copy(alpha = 0.5f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = text,
                fontSize = 13.sp,
                color = if (completed) Color.White.copy(alpha = 0.7f) else Color.White,
                modifier = Modifier.alpha(if (completed) 0.7f else 1f)
            )
        }
    }
}

/**
 * Notes tile renderer.
 */
object NotesTileRenderer : TileRenderer {
    @Composable
    override fun Render(tile: DashboardTile, isDragging: Boolean, modifier: Modifier) {
        TileCard(
            title = tile.title,
            iconText = "📝",
            isDragging = isDragging,
            backgroundColor = Color(0xFFF59E0B),
            modifier = modifier
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "Remember to...",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
                Text(
                    text = "- Update documentation\n- Review pull requests\n- Prepare presentation",
                    fontSize = 12.sp,
                    color = Color.White.copy(alpha = 0.9f),
                    lineHeight = 18.sp
                )
            }
        }
    }
}

/**
 * Empty/fallback tile renderer.
 */
object EmptyTileRenderer : TileRenderer {
    @Composable
    override fun Render(tile: DashboardTile, isDragging: Boolean, modifier: Modifier) {
        Card(
            modifier = modifier
                .fillMaxSize()
                .border(
                    width = 2.dp,
                    color = Color.Gray.copy(alpha = 0.3f),
                    shape = RoundedCornerShape(16.dp)
                ),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.LightGray.copy(alpha = 0.1f)
            )
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = tile.title,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }
        }
    }
}
