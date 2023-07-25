package uz.javokhir.picker

import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import uz.javokhir.picker.core.DefaultWheelTimePicker
import uz.javokhir.picker.core.SelectorProperties
import uz.javokhir.picker.core.TimeFormat
import uz.javokhir.picker.core.WheelPickerDefaults
import java.time.LocalTime

@Composable
fun WheelTimePicker(
    modifier: Modifier = Modifier,
    startTime: LocalTime = LocalTime.now(),
    minTime: LocalTime = LocalTime.MIN,
    maxTime: LocalTime = LocalTime.MAX,
    timeFormat: TimeFormat = TimeFormat.HOUR_24,
    size: DpSize = DpSize(140.dp, 140.dp),
    rowCount: Int = 3,
    textStyle: TextStyle = MaterialTheme.typography.titleLarge,
    textColor: Color = LocalContentColor.current,
    selectorProperties: SelectorProperties = WheelPickerDefaults.selectorProperties(),
    onSnappedTime: (snappedTime: LocalTime) -> Unit = {},
) {
    DefaultWheelTimePicker(
        modifier = modifier,
        startTime = startTime,
        minTime = minTime,
        maxTime = maxTime,
        timeFormat = timeFormat,
        size = size,
        rowCount = rowCount,
        textStyle = textStyle,
        textColor = textColor,
        selectorProperties = selectorProperties,
        onSnappedTime = { snappedTime, _ ->
            onSnappedTime(snappedTime.snappedLocalTime)
            snappedTime.snappedIndex
        }
    )
}