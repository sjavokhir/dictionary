package com.translator.uzbek.english.dictionary.android.design.components.chart

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.patrykandpatrick.vico.compose.axis.horizontal.bottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.startAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.column.columnChart
import com.patrykandpatrick.vico.compose.style.ProvideChartStyle
import com.patrykandpatrick.vico.compose.style.currentChartStyle
import com.patrykandpatrick.vico.core.DefaultDimens
import com.patrykandpatrick.vico.core.chart.column.ColumnChart
import com.patrykandpatrick.vico.core.component.shape.LineComponent
import com.patrykandpatrick.vico.core.component.shape.Shapes
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.translator.uzbek.english.dictionary.android.design.theme.ChartColor1
import com.translator.uzbek.english.dictionary.android.design.theme.ChartColor2
import com.translator.uzbek.english.dictionary.android.design.theme.ChartColor3
import com.translator.uzbek.english.dictionary.android.design.theme.ChartColor4

@Composable
fun StackedBarChart(
    modifier: Modifier = Modifier,
    chartEntryModelProducer: ChartEntryModelProducer
) {
    ProvideChartStyle(rememberChartStyle(chartColors)) {
        val defaultColumns = currentChartStyle.columnChart.columns

        Chart(
            modifier = modifier,
            chart = columnChart(
                columns = remember(defaultColumns) {
                    defaultColumns.mapIndexed { index, defaultColumn ->
                        val topCornerRadiusPercent =
                            if (index == defaultColumns.lastIndex) DefaultDimens.COLUMN_ROUNDNESS_PERCENT else 0
                        val bottomCornerRadiusPercent =
                            if (index == 0) DefaultDimens.COLUMN_ROUNDNESS_PERCENT else 0

                        LineComponent(
                            defaultColumn.color,
                            defaultColumn.thicknessDp,
                            Shapes.roundedCornerShape(
                                topCornerRadiusPercent,
                                topCornerRadiusPercent,
                                bottomCornerRadiusPercent,
                                bottomCornerRadiusPercent,
                            ),
                        )
                    }
                },
                mergeMode = ColumnChart.MergeMode.Stack,
            ),
            chartModelProducer = chartEntryModelProducer,
            startAxis = startAxis(
                maxLabelCount = START_AXIS_LABEL_COUNT,
                labelRotationDegrees = AXIS_LABEL_ROTATION_DEGREES,
            ),
            bottomAxis = bottomAxis(labelRotationDegrees = AXIS_LABEL_ROTATION_DEGREES),
            marker = rememberMarker(),
        )
    }
}

private const val START_AXIS_LABEL_COUNT = 4
private const val AXIS_LABEL_ROTATION_DEGREES = 0f

private val chartColors = listOf(ChartColor1, ChartColor2, ChartColor3, ChartColor4)
