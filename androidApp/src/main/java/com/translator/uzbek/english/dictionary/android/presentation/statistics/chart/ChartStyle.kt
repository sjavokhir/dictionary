package com.translator.uzbek.english.dictionary.android.presentation.statistics.chart

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.patrykandpatrick.vico.compose.component.shape.shader.fromBrush
import com.patrykandpatrick.vico.compose.style.ChartStyle
import com.patrykandpatrick.vico.core.DefaultAlpha
import com.patrykandpatrick.vico.core.DefaultDimens
import com.patrykandpatrick.vico.core.chart.line.LineChart
import com.patrykandpatrick.vico.core.component.shape.LineComponent
import com.patrykandpatrick.vico.core.component.shape.Shapes
import com.patrykandpatrick.vico.core.component.shape.shader.DynamicShaders

@Composable
fun rememberChartStyle(
    axisLabelColor: Color = MaterialTheme.colorScheme.outline,
    axisGuidelineColor: Color = MaterialTheme.colorScheme.outline.copy(0.2f),
    axisLineColor: Color = MaterialTheme.colorScheme.outline.copy(0.2f),
    elevationOverlayColor: Color = MaterialTheme.colorScheme.background,
    columnChartColors: List<Color>,
    lineChartColors: List<Color>
): ChartStyle {
    return remember(columnChartColors, lineChartColors) {
        ChartStyle(
            ChartStyle.Axis(
                axisLabelColor = axisLabelColor,
                axisGuidelineColor = axisGuidelineColor,
                axisLineColor = axisLineColor,
            ),
            ChartStyle.ColumnChart(
                columnChartColors.map { columnChartColor ->
                    LineComponent(
                        columnChartColor.toArgb(),
                        DefaultDimens.COLUMN_WIDTH,
                        Shapes.roundedCornerShape(DefaultDimens.COLUMN_ROUNDNESS_PERCENT),
                    )
                },
            ),
            ChartStyle.LineChart(
                lineChartColors.map { lineChartColor ->
                    LineChart.LineSpec(
                        lineColor = lineChartColor.toArgb(),
                        lineBackgroundShader = DynamicShaders.fromBrush(
                            Brush.verticalGradient(
                                listOf(
                                    lineChartColor.copy(DefaultAlpha.LINE_BACKGROUND_SHADER_START),
                                    lineChartColor.copy(DefaultAlpha.LINE_BACKGROUND_SHADER_END),
                                ),
                            ),
                        ),
                    )
                },
            ),
            ChartStyle.Marker(),
            elevationOverlayColor,
        )
    }
}

@Composable
fun rememberChartStyle(
    chartColors: List<Color>
) = rememberChartStyle(
    columnChartColors = chartColors,
    lineChartColors = chartColors
)
