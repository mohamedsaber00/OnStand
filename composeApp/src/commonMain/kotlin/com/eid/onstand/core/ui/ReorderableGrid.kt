package com.eid.onstand.core.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.VectorConverter
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import kotlinx.coroutines.launch

/**
 * Represents the bounds and position of a grid item.
 */
internal data class GridItemBounds(
    val position: Offset = Offset.Zero,
    val size: IntSize = IntSize.Zero
) {
    val centerX: Float get() = position.x + size.width / 2f
    val centerY: Float get() = position.y + size.height / 2f

    fun contains(point: Offset): Boolean {
        return point.x >= position.x && point.x <= position.x + size.width &&
               point.y >= position.y && point.y <= position.y + size.height
    }
}

/**
 * State for managing reorderable grid with drag and drop functionality.
 * Uses item keys for stable identity across swaps.
 */
class ReorderableGridState<T>(
    initialItems: List<T>,
    private val onReorder: (List<T>) -> Unit,
    private val scope: kotlinx.coroutines.CoroutineScope,
    private val keySelector: (T) -> Any
) {
    internal var items by mutableStateOf(initialItems)

    // Track which item is being dragged (by its key)
    internal var draggingKey by mutableStateOf<Any?>(null)

    // Store grid container coordinates for reference
    internal var gridCoordinates by mutableStateOf<LayoutCoordinates?>(null)

    // Map from item key to physical bounds (in grid coordinate space)
    internal val itemBounds = mutableStateMapOf<Any, GridItemBounds>()

    // Map from item key to its animated offset
    internal val offsets = mutableStateMapOf<Any, Animatable<Offset, androidx.compose.animation.core.AnimationVector2D>>()

    // Track which item key is being hovered over
    internal var hoveredItemKey by mutableStateOf<Any?>(null)

    internal fun startDrag(itemKey: Any) {
        draggingKey = itemKey
        hoveredItemKey = null
    }

    internal suspend fun updateDrag(itemKey: Any, dragAmount: Offset) {
        if (draggingKey != itemKey) return

        // Update the dragged item's offset
        val currentOffset = offsets[itemKey]?.targetValue ?: Offset.Zero
        offsets.getOrPut(itemKey) {
            Animatable(Offset.Zero, Offset.VectorConverter)
        }.snapTo(currentOffset + dragAmount)

        // Calculate current center of the dragged item
        val draggedBounds = itemBounds[itemKey] ?: return
        val draggedOffset = offsets[itemKey]?.value ?: Offset.Zero
        val draggedCurrentCenter = Offset(
            draggedBounds.centerX + draggedOffset.x,
            draggedBounds.centerY + draggedOffset.y
        )

        // Check which item we're hovering over
        var newHoveredItemKey: Any? = null
        itemBounds.forEach { (otherKey, bounds) ->
            if (otherKey != itemKey && bounds.contains(draggedCurrentCenter)) {
                newHoveredItemKey = otherKey
            }
        }

        // If hovered item changed, update animations
        if (newHoveredItemKey != hoveredItemKey) {
            val previousHoveredKey = hoveredItemKey
            hoveredItemKey = newHoveredItemKey

            // Reset previous hovered item's offset
            if (previousHoveredKey != null) {
                scope.launch {
                    offsets.getOrPut(previousHoveredKey) {
                        Animatable(Offset.Zero, Offset.VectorConverter)
                    }.animateTo(Offset.Zero, spring(stiffness = Spring.StiffnessMediumLow))
                }
            }

            // Animate new hovered item to swap position
            if (newHoveredItemKey != null) {
                val draggedOriginalPos = itemBounds[itemKey]?.position ?: return
                val hoveredOriginalPos = itemBounds[newHoveredItemKey]?.position ?: return
                val offsetToApply = Offset(
                    draggedOriginalPos.x - hoveredOriginalPos.x,
                    draggedOriginalPos.y - hoveredOriginalPos.y
                )
                scope.launch {
                    offsets.getOrPut(newHoveredItemKey) {
                        Animatable(Offset.Zero, Offset.VectorConverter)
                    }.animateTo(offsetToApply, spring(stiffness = Spring.StiffnessMediumLow))
                }
            }
        }
    }

    internal suspend fun stopDrag(itemKey: Any) {
        val targetItemKey = hoveredItemKey

        // Perform the swap immediately if there's a target
        if (targetItemKey != null && targetItemKey != itemKey) {
            // Find indices of the two items
            val fromIndex = items.indexOfFirst { keySelector(it) == itemKey }
            val toIndex = items.indexOfFirst { keySelector(it) == targetItemKey }

            if (fromIndex != -1 && toIndex != -1) {
                // Clear dragging state FIRST
                draggingKey = null
                hoveredItemKey = null

                // Clear all offsets immediately - items are already visually in the right place
                offsets.clear()

                // Now swap the data - items will render in their new positions with zero offset
                val newItems = items.toMutableList()
                val temp = newItems[fromIndex]
                newItems[fromIndex] = newItems[toIndex]
                newItems[toIndex] = temp
                items = newItems

                // Notify immediately
                onReorder(newItems)
            }
        } else {
            // No swap, animate back to original position
            draggingKey = null
            hoveredItemKey = null

            kotlinx.coroutines.coroutineScope {
                offsets.forEach { (key, animatable) ->
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

/**
 * Creates and remembers a ReorderableGridState.
 */
@Composable
fun <T> rememberReorderableGridState(
    items: List<T>,
    onReorder: (List<T>) -> Unit,
    key: (T) -> Any = { it.hashCode() }
): ReorderableGridState<T> {
    val scope = rememberCoroutineScope()
    val state = remember(items.size) {
        ReorderableGridState(items, onReorder, scope, key)
    }
    // Update items when they change externally
    LaunchedEffect(items) {
        if (state.items != items && state.draggingKey == null) {
            state.items = items
        }
    }
    return state
}

/**
 * A reorderable grid that supports drag and drop to reorder items.
 *
 * @param columns Number of columns in the grid. Defaults to 2 for a 2x2 grid.
 */
@Composable
fun <T> ReorderableGrid(
    items: List<T>,
    state: ReorderableGridState<T>,
    modifier: Modifier = Modifier,
    columns: Int = 2,
    key: (T) -> Any = { it.hashCode() },
    content: @Composable (item: T, index: Int, isDragging: Boolean) -> Unit
) {
    val scope = rememberCoroutineScope()

    // Calculate number of rows needed
    val rows = (items.size + columns - 1) / columns

    // Wrap in Box to establish a common coordinate space
    Box(
        modifier = modifier
            .fillMaxSize()
            .onGloballyPositioned { coordinates ->
                state.gridCoordinates = coordinates
            }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Create rows dynamically
            for (rowIndex in 0 until rows) {
                Row(
                    modifier = Modifier.weight(1f).fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Create columns for this row
                    for (colIndex in 0 until columns) {
                        val itemIndex = rowIndex * columns + colIndex

                        if (itemIndex < items.size) {
                            val item = items[itemIndex]
                            key(key(item)) {
                                ReorderableGridItem(
                                    item = item,
                                    itemKey = key(item),
                                    state = state,
                                    scope = scope,
                                    modifier = Modifier.weight(1f).fillMaxHeight(),
                                    content = content,
                                    index = itemIndex
                                )
                            }
                        } else {
                            // Empty space for incomplete rows
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun <T> ReorderableGridItem(
    item: T,
    itemKey: Any,
    state: ReorderableGridState<T>,
    scope: kotlinx.coroutines.CoroutineScope,
    modifier: Modifier = Modifier,
    content: @Composable (item: T, index: Int, isDragging: Boolean) -> Unit,
    index: Int
) {
    val isDragging = state.draggingKey == itemKey
    val offset = state.offsets[itemKey]?.value ?: Offset.Zero

    Box(
        modifier = modifier
            .onGloballyPositioned { coordinates ->
                // Calculate position relative to grid container
                val gridCoords = state.gridCoordinates
                if (gridCoords != null && gridCoords.isAttached && coordinates.isAttached) {
                    // Get position in window for both
                    val itemPosInWindow = coordinates.positionInWindow()
                    val gridPosInWindow = gridCoords.positionInWindow()

                    // Calculate relative position
                    val relativePos = Offset(
                        itemPosInWindow.x - gridPosInWindow.x,
                        itemPosInWindow.y - gridPosInWindow.y
                    )

                    state.itemBounds[itemKey] = GridItemBounds(
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
            .pointerInput(itemKey) {
                detectDragGestures(
                    onDragStart = {
                        state.startDrag(itemKey)
                    },
                    onDrag = { change, dragAmount ->
                        change.consume()
                        scope.launch {
                            state.updateDrag(itemKey, dragAmount)
                        }
                    },
                    onDragEnd = {
                        scope.launch {
                            state.stopDrag(itemKey)
                        }
                    },
                    onDragCancel = {
                        scope.launch {
                            state.stopDrag(itemKey)
                        }
                    }
                )
            }
    ) {
        content(item, index, isDragging)
    }
}
