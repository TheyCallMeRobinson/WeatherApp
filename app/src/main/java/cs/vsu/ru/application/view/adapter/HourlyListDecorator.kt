package cs.vsu.ru.application.view.adapter

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.CornerPathEffect
import android.graphics.Paint
import android.graphics.Path
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import cs.vsu.ru.application.R
import cs.vsu.ru.application.model.HourlyWeather

private const val STROKE_WIDTH = 10f
private const val PATH_CORNER_RADIUS_IN_DP = 30
private const val CHILD_HEADER_HEIGHT_IN_DP = 130
private const val CHILD_FOOTER_HEIGHT_IN_DP = 50

internal class HourlyTemperatureGraphCurvyItemDecorator(dayStocksUIModels: List<HourlyWeather>, context: Context) :
    RecyclerView.ItemDecoration() {

    private val drawPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        strokeWidth = STROKE_WIDTH
        style = Paint.Style.STROKE
        strokeJoin = Paint.Join.ROUND
        strokeCap = Paint.Cap.ROUND
        color = ContextCompat.getColor(context, R.color.transparent60_white)
        pathEffect = CornerPathEffect(PATH_CORNER_RADIUS_IN_DP.dpToPx)
    }

    private val normalizedHourlyTemperatureValues = normalizeDayStockValues(dayStocksUIModels)

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(canvas, parent, state)

        val path = Path()
        var newPath = true

        for (childIndex in 0 until parent.childCount) {
            val childView = parent.getChildAt(childIndex)
            val dataIndex = parent.getChildAdapterPosition(childView)
            val childViewHeight = childView.height
            val halfChildViewWidth = (childView.right.toFloat() - childView.left.toFloat()) / 2

            if (newPath) {
                val previousDataIndex = if (dataIndex > 0) (dataIndex - 1) else 0
                val moveToYPosition = calculateYValue(previousDataIndex, childViewHeight)
                path.moveTo(childView.left.toFloat() - halfChildViewWidth, moveToYPosition)
                newPath = false
            }

            val calculatedYValue = calculateYValue(dataIndex, childViewHeight)

            path.lineTo(childView.right.toFloat() - halfChildViewWidth, calculatedYValue)

            if (childIndex == parent.childCount - 1) {
                drawPathForNextChildView(
                    dataIndex + 1,
                    childView.right.toFloat(),
                    path,
                    halfChildViewWidth,
                    childViewHeight
                )
            }
        }

        canvas.drawPath(path, drawPaint)
    }

    private fun drawPathForNextChildView(
        nextChildViewDataIndex: Int,
        nextChildViewMiddleXValue: Float,
        path: Path,
        halfChildViewWidth: Float,
        childViewHeight: Int
    ) {
        if (nextChildViewDataIndex >= normalizedHourlyTemperatureValues.size) {
            handleNextAfterLastChildView(nextChildViewMiddleXValue, path, childViewHeight)
        } else {
            val nextChildViewEndXValue = nextChildViewMiddleXValue + halfChildViewWidth
            path.lineTo(nextChildViewEndXValue, calculateYValue(nextChildViewDataIndex, childViewHeight))
        }
    }

    private fun handleNextAfterLastChildView(
        lastXValue: Float,
        path: Path,
        childViewHeight: Int
    ) {
        path.lineTo(lastXValue, calculateYValue(normalizedHourlyTemperatureValues.size - 1, childViewHeight))
    }

    private fun calculateYValue(dataIndex: Int, childViewHeight: Int): Float {
        val graphHeight = childViewHeight - (CHILD_HEADER_HEIGHT_IN_DP + CHILD_FOOTER_HEIGHT_IN_DP).dpToPx
        val graphStartHeightDelta = (CHILD_HEADER_HEIGHT_IN_DP).dpToPx

        return ((1 - normalizedHourlyTemperatureValues[dataIndex]) * graphHeight + graphStartHeightDelta).toFloat()
    }

    private fun normalizeDayStockValues(hourlyWeatherList: List<HourlyWeather>): List<Double> {
        val minTemperature = hourlyWeatherList.minBy { it.temperature }
        val maxTemperature = hourlyWeatherList.maxBy { it.temperature }

        if (minTemperature.temperature >= maxTemperature.temperature) {
            return hourlyWeatherList.map { 0.5 }
        }

        val range = maxTemperature.temperature - minTemperature.temperature
        return hourlyWeatherList.map {
            val relativeValue = it.temperature - minTemperature.temperature * 1.0
            return@map (relativeValue / range)
        }
    }
}

private val Int.dpToPx: Float
    get() = (this * Resources.getSystem().displayMetrics.density)