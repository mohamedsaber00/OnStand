package com.eid.onstand.feature.dashboard

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.eid.onstand.core.models.DashboardLayout
import com.eid.onstand.core.models.DashboardTile
import com.eid.onstand.core.ui.theme.Colors
import com.eid.onstand.core.ui.theme.GradientColors
import com.eid.onstand.feature.dashboard.customization.LayoutThumbnail
import com.eid.onstand.feature.dashboard.tiles.TileRenderers
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

/**
 * Dashboard customization screen for selecting layout and reordering tiles.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit = {},
    viewModel: DashboardViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Brush.linearGradient(GradientColors.SCREEN_BACKGROUND))
    ) {
        if (uiState.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            Column(modifier = Modifier.fillMaxSize()) {
                // Header
                TopAppBar(
                    title = {
                        Text(
                            text = "Dashboard Layout",
                            color = Colors.TextPrimary,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium
                        )
                    },
                    navigationIcon = {
                        TextButton(onClick = {
                            viewModel.discardChanges()
                            onBackPressed()
                        }) {
                            Text("<-", color = Colors.TextPrimary, fontSize = 24.sp)
                        }
                    },
                    actions = {
                        TextButton(onClick = { viewModel.resetToDefault() }) {
                            Text(
                                text = "Reset",
                                color = Colors.TextPrimary.copy(alpha = 0.7f),
                                fontSize = 14.sp
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Colors.Transparent
                    )
                )

                // Content
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                ) {
                    // Preview with drag-and-drop
                    Text(
                        text = "Preview - Drag tiles to reorder",
                        color = Colors.TextPrimary.copy(alpha = 0.7f),
                        fontSize = 12.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    PreviewCard(
                        layout = uiState.layout,
                        tiles = uiState.tiles,
                        onTilesReordered = viewModel::onTilesReordered,
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.45f)
                            .padding(bottom = 16.dp)
                    )

                    // Layout Selection
                    Text(
                        text = "Choose Layout",
                        color = Colors.TextPrimary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    LayoutSelectionRow(
                        layouts = DashboardLayout.entries,
                        selectedLayout = uiState.layout,
                        onLayoutSelected = viewModel::selectLayout
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Layout info
                    LayoutInfoCard(layout = uiState.layout)

                    Spacer(modifier = Modifier.weight(1f))
                }
            }

            // Bottom Action Buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(Colors.Black.copy(alpha = 0.5f))
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedButton(
                    onClick = {
                        viewModel.discardChanges()
                        onBackPressed()
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Colors.TextPrimary,
                        containerColor = Colors.ButtonBackground
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Cancel",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }

                Button(
                    onClick = { viewModel.applySettings { onBackPressed() } },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Colors.ButtonPrimaryTransparent
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Apply",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Colors.TextPrimary,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun PreviewCard(
    layout: DashboardLayout,
    tiles: List<DashboardTile>,
    onTilesReordered: (List<DashboardTile>) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Colors.BackgroundCardTransparent
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            ReorderableLayoutContent(
                layout = layout,
                tiles = tiles,
                onTilesReordered = onTilesReordered
            )
        }
    }
}

@Composable
private fun LayoutSelectionRow(
    layouts: List<DashboardLayout>,
    selectedLayout: DashboardLayout,
    onLayoutSelected: (DashboardLayout) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(layouts) { layout ->
            val isSelected = layout == selectedLayout
            val scale by animateFloatAsState(
                targetValue = if (isSelected) 1.05f else 1f,
                animationSpec = tween(200)
            )

            LayoutOptionCard(
                layout = layout,
                isSelected = isSelected,
                onSelected = { onLayoutSelected(layout) },
                modifier = Modifier.scale(scale)
            )
        }
    }
}

@Composable
private fun LayoutOptionCard(
    layout: DashboardLayout,
    isSelected: Boolean,
    onSelected: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .width(100.dp)
            .clickable { onSelected() }
            .then(
                if (isSelected) {
                    Modifier.border(
                        width = 2.dp,
                        color = Colors.SelectionBorder,
                        shape = RoundedCornerShape(12.dp)
                    )
                } else {
                    Modifier
                }
            ),
        colors = CardDefaults.cardColors(
            containerColor = Colors.BackgroundCardTransparent
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.2f)
            ) {
                LayoutThumbnail(
                    layout = layout,
                    modifier = Modifier.fillMaxSize()
                )

                if (isSelected) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(4.dp)
                            .size(16.dp)
                            .background(
                                color = Colors.SelectionIndicator,
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "✓",
                            fontSize = 10.sp,
                            color = Colors.TextPrimary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = layout.displayName,
                fontSize = 11.sp,
                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                color = Colors.TextPrimary,
                textAlign = TextAlign.Center,
                maxLines = 1
            )
        }
    }
}

@Composable
private fun LayoutInfoCard(
    layout: DashboardLayout,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Colors.BackgroundCardTransparent.copy(alpha = 0.5f)
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            InfoItem(label = "Tiles", value = "${layout.maxTiles}")
            InfoItem(label = "Columns", value = "${layout.columns}")
            InfoItem(label = "Rows", value = "${layout.rows}")
        }
    }
}

@Composable
private fun InfoItem(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Colors.TextPrimary
        )
        Text(
            text = label,
            fontSize = 12.sp,
            color = Colors.TextPrimary.copy(alpha = 0.7f)
        )
    }
}

// ==================== Drag and Drop Implementation ====================

/**
 * Represents the bounds and position of a tile item.
 */
private data class TileItemBounds(
    val position: Offset = Offset.Zero,
    val size: androidx.compose.ui.unit.IntSize = androidx.compose.ui.unit.IntSize.Zero
) {
    val centerX: Float get() = position.x + size.width / 2f
    val centerY: Float get() = position.y + size.height / 2f

    fun contains(point: Offset): Boolean {
        return point.x >= position.x && point.x <= position.x + size.width &&
               point.y >= position.y && point.y <= position.y + size.height
    }
}

/**
 * State class to manage drag and drop for layout tiles.
 */
private class LayoutDragState(
    initialTiles: List<DashboardTile>,
    private val onReorder: (List<DashboardTile>) -> Unit,
    private val scope: kotlinx.coroutines.CoroutineScope
) {
    var tiles by mutableStateOf(initialTiles)
    var draggingId by mutableStateOf<String?>(null)
    var containerCoordinates by mutableStateOf<androidx.compose.ui.layout.LayoutCoordinates?>(null)
    val tileBounds = mutableStateMapOf<String, TileItemBounds>()
    val offsets = mutableStateMapOf<String, Animatable<Offset, androidx.compose.animation.core.AnimationVector2D>>()
    var hoveredId by mutableStateOf<String?>(null)

    fun updateTiles(newTiles: List<DashboardTile>) {
        if (draggingId == null && tiles != newTiles) {
            tiles = newTiles
        }
    }

    fun startDrag(id: String) {
        draggingId = id
        hoveredId = null
    }

    suspend fun updateDrag(id: String, dragAmount: Offset) {
        if (draggingId != id) return

        val currentOffset = offsets[id]?.targetValue ?: Offset.Zero
        offsets.getOrPut(id) {
            Animatable(Offset.Zero, Offset.VectorConverter)
        }.snapTo(currentOffset + dragAmount)

        // Calculate current center of the dragged item
        val draggedBounds = tileBounds[id] ?: return
        val dragOffset = offsets[id]?.value ?: Offset.Zero
        val draggedCurrentCenter = Offset(
            draggedBounds.centerX + dragOffset.x,
            draggedBounds.centerY + dragOffset.y
        )

        // Check which tile we're hovering over
        var newHoveredId: String? = null
        tileBounds.forEach { (otherId, bounds) ->
            if (otherId != id && bounds.contains(draggedCurrentCenter)) {
                newHoveredId = otherId
            }
        }

        // If hovered item changed, update animations
        if (newHoveredId != hoveredId) {
            val previousHoveredId = hoveredId
            hoveredId = newHoveredId

            // Reset previous hovered item's offset
            if (previousHoveredId != null) {
                scope.launch {
                    offsets.getOrPut(previousHoveredId) {
                        Animatable(Offset.Zero, Offset.VectorConverter)
                    }.animateTo(Offset.Zero, spring(stiffness = Spring.StiffnessMediumLow))
                }
            }

            // Animate new hovered item to swap position
            if (newHoveredId != null) {
                val draggedOriginalPos = tileBounds[id]?.position ?: return
                val hoveredOriginalPos = tileBounds[newHoveredId]?.position ?: return
                val offsetToApply = Offset(
                    draggedOriginalPos.x - hoveredOriginalPos.x,
                    draggedOriginalPos.y - hoveredOriginalPos.y
                )
                scope.launch {
                    offsets.getOrPut(newHoveredId) {
                        Animatable(Offset.Zero, Offset.VectorConverter)
                    }.animateTo(offsetToApply, spring(stiffness = Spring.StiffnessMediumLow))
                }
            }
        }
    }

    suspend fun stopDrag(id: String) {
        val targetId = hoveredId

        // Perform the swap immediately if there's a target
        if (targetId != null && targetId != id) {
            val fromIndex = tiles.indexOfFirst { it.id == id }
            val toIndex = tiles.indexOfFirst { it.id == targetId }

            if (fromIndex != -1 && toIndex != -1) {
                // Clear dragging state FIRST
                draggingId = null
                hoveredId = null

                // Clear all offsets immediately - items are already visually in the right place
                offsets.clear()

                // Now swap the data - items will render in their new positions with zero offset
                val newTiles = tiles.toMutableList()
                val temp = newTiles[fromIndex]
                newTiles[fromIndex] = newTiles[toIndex]
                newTiles[toIndex] = temp
                tiles = newTiles

                // Notify immediately
                onReorder(newTiles)
            }
        } else {
            // No swap, animate back to original position
            draggingId = null
            hoveredId = null

            kotlinx.coroutines.coroutineScope {
                offsets.forEach { (_, animatable) ->
                    launch {
                        animatable.animateTo(Offset.Zero, spring(stiffness = Spring.StiffnessMediumLow))
                    }
                }
            }

            kotlinx.coroutines.delay(150)
            offsets.clear()
        }
    }
}

@Composable
private fun ReorderableLayoutContent(
    layout: DashboardLayout,
    tiles: List<DashboardTile>,
    onTilesReordered: (List<DashboardTile>) -> Unit
) {
    val scope = rememberCoroutineScope()
    val state = remember(tiles.size) { LayoutDragState(tiles, onTilesReordered, scope) }
    state.updateTiles(tiles)

    // Wrap in Box to establish a common coordinate space
    Box(
        modifier = Modifier
            .fillMaxSize()
            .onGloballyPositioned { coordinates ->
                state.containerCoordinates = coordinates
            }
    ) {
        when (layout) {
            DashboardLayout.SINGLE -> SingleLayout(tiles, state)
            DashboardLayout.TWO_HORIZONTAL -> TwoHorizontalLayout(tiles, state)
            DashboardLayout.TWO_VERTICAL -> TwoVerticalLayout(tiles, state)
            DashboardLayout.THREE_LEFT_DOMINANT -> ThreeLeftDominantLayout(tiles, state)
            DashboardLayout.THREE_RIGHT_DOMINANT -> ThreeRightDominantLayout(tiles, state)
            DashboardLayout.GRID_2X2 -> Grid2x2Layout(tiles, state)
        }
    }
}

@Composable
private fun DraggableTile(
    tile: DashboardTile,
    state: LayoutDragState,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    val isDragging = state.draggingId == tile.id
    val offset = state.offsets[tile.id]?.value ?: Offset.Zero

    Box(
        modifier = modifier
            .onGloballyPositioned { coordinates ->
                // Calculate position relative to container (like the original ReorderableGrid)
                val containerCoords = state.containerCoordinates
                if (containerCoords != null && containerCoords.isAttached && coordinates.isAttached) {
                    // Get position in window for both
                    val itemPosInWindow = coordinates.positionInWindow()
                    val containerPosInWindow = containerCoords.positionInWindow()

                    // Calculate relative position
                    val relativePos = Offset(
                        itemPosInWindow.x - containerPosInWindow.x,
                        itemPosInWindow.y - containerPosInWindow.y
                    )

                    state.tileBounds[tile.id] = TileItemBounds(
                        position = relativePos,
                        size = coordinates.size
                    )
                }
            }
            .graphicsLayer {
                translationX = offset.x
                translationY = offset.y
                scaleX = if (isDragging) 1.05f else 1f
                scaleY = if (isDragging) 1.05f else 1f
            }
            .zIndex(if (isDragging) 1f else 0f)
            .pointerInput(tile.id) {
                detectDragGestures(
                    onDragStart = { state.startDrag(tile.id) },
                    onDrag = { change, dragAmount ->
                        change.consume()
                        scope.launch {
                            state.updateDrag(tile.id, dragAmount)
                        }
                    },
                    onDragEnd = {
                        scope.launch { state.stopDrag(tile.id) }
                    },
                    onDragCancel = {
                        scope.launch { state.stopDrag(tile.id) }
                    }
                )
            }
    ) {
        RenderTile(tile = tile, isDragging = isDragging, modifier = Modifier.fillMaxSize())
    }
}

@Composable
private fun SingleLayout(tiles: List<DashboardTile>, state: LayoutDragState) {
    Box(modifier = Modifier.fillMaxSize()) {
        tiles.getOrNull(0)?.let { tile ->
            key(tile.id) {
                DraggableTile(tile = tile, state = state, modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
private fun TwoHorizontalLayout(tiles: List<DashboardTile>, state: LayoutDragState) {
    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        tiles.take(2).forEach { tile ->
            key(tile.id) {
                DraggableTile(
                    tile = tile,
                    state = state,
                    modifier = Modifier.weight(1f).fillMaxHeight()
                )
            }
        }
    }
}

@Composable
private fun TwoVerticalLayout(tiles: List<DashboardTile>, state: LayoutDragState) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        tiles.take(2).forEach { tile ->
            key(tile.id) {
                DraggableTile(
                    tile = tile,
                    state = state,
                    modifier = Modifier.weight(1f).fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun ThreeLeftDominantLayout(tiles: List<DashboardTile>, state: LayoutDragState) {
    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        tiles.getOrNull(0)?.let { tile ->
            key(tile.id) {
                DraggableTile(
                    tile = tile,
                    state = state,
                    modifier = Modifier.weight(1f).fillMaxHeight()
                )
            }
        }

        Column(
            modifier = Modifier.weight(1f).fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            tiles.getOrNull(1)?.let { tile ->
                key(tile.id) {
                    DraggableTile(
                        tile = tile,
                        state = state,
                        modifier = Modifier.weight(1f).fillMaxWidth()
                    )
                }
            }
            tiles.getOrNull(2)?.let { tile ->
                key(tile.id) {
                    DraggableTile(
                        tile = tile,
                        state = state,
                        modifier = Modifier.weight(1f).fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Composable
private fun ThreeRightDominantLayout(tiles: List<DashboardTile>, state: LayoutDragState) {
    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Column(
            modifier = Modifier.weight(1f).fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            tiles.getOrNull(0)?.let { tile ->
                key(tile.id) {
                    DraggableTile(
                        tile = tile,
                        state = state,
                        modifier = Modifier.weight(1f).fillMaxWidth()
                    )
                }
            }
            tiles.getOrNull(1)?.let { tile ->
                key(tile.id) {
                    DraggableTile(
                        tile = tile,
                        state = state,
                        modifier = Modifier.weight(1f).fillMaxWidth()
                    )
                }
            }
        }

        tiles.getOrNull(2)?.let { tile ->
            key(tile.id) {
                DraggableTile(
                    tile = tile,
                    state = state,
                    modifier = Modifier.weight(1f).fillMaxHeight()
                )
            }
        }
    }
}

@Composable
private fun Grid2x2Layout(tiles: List<DashboardTile>, state: LayoutDragState) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.weight(1f).fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            tiles.getOrNull(0)?.let { tile ->
                key(tile.id) {
                    DraggableTile(
                        tile = tile,
                        state = state,
                        modifier = Modifier.weight(1f).fillMaxHeight()
                    )
                }
            }
            tiles.getOrNull(1)?.let { tile ->
                key(tile.id) {
                    DraggableTile(
                        tile = tile,
                        state = state,
                        modifier = Modifier.weight(1f).fillMaxHeight()
                    )
                }
            }
        }

        Row(
            modifier = Modifier.weight(1f).fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            tiles.getOrNull(2)?.let { tile ->
                key(tile.id) {
                    DraggableTile(
                        tile = tile,
                        state = state,
                        modifier = Modifier.weight(1f).fillMaxHeight()
                    )
                }
            }
            tiles.getOrNull(3)?.let { tile ->
                key(tile.id) {
                    DraggableTile(
                        tile = tile,
                        state = state,
                        modifier = Modifier.weight(1f).fillMaxHeight()
                    )
                }
            }
        }
    }
}

@Composable
private fun RenderTile(
    tile: DashboardTile,
    isDragging: Boolean,
    modifier: Modifier = Modifier
) {
    val renderer = TileRenderers.getRenderer(tile.type)
    renderer.Render(
        tile = tile,
        isDragging = isDragging,
        modifier = modifier
    )
}
