@file:OptIn(ExperimentalMaterial3Api::class)

package com.eazywrite.app.ui.chart
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.eazywrite.app.R
import com.eazywrite.app.ui.theme.EazyWriteTheme
import com.jaikeerthick.composable_graphs.color.*
import com.jaikeerthick.composable_graphs.composables.LineGraph
import com.jaikeerthick.composable_graphs.data.GraphData
import com.jaikeerthick.composable_graphs.style.LabelPosition
import com.jaikeerthick.composable_graphs.style.LineGraphStyle
import com.jaikeerthick.composable_graphs.style.LinearGraphVisibility

class ChartActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = Color.Transparent.toArgb()
        window.navigationBarColor = Color.Transparent.toArgb()
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = true

        setContent {
            EazyWriteTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    ChartPage()
                }
            }
        }
    }
}

@Composable
fun ChartPage() {
    Scaffold(topBar = {
        TopAppBar(title = {
            Text(text = stringResource(id = R.string.chart))
        })
    }) {
        Box(modifier = Modifier.padding(it)) {
            val clickedValue: MutableState<Pair<Any, Any>?> =
                remember { mutableStateOf(null) }
            val style2 = LineGraphStyle(
                visibility = LinearGraphVisibility(
                    isHeaderVisible = true,
                    isYAxisLabelVisible = true,
                    isCrossHairVisible = true,
                    isGridVisible = true
                ),
                colors = LinearGraphColors(
                    lineColor = GraphAccent2,
                    pointColor = GraphAccent2,
                    clickHighlightColor = PointHighlight2,
                    fillGradient = Brush.verticalGradient(
                        listOf(Gradient3, Gradient2)
                    )
                ),
                yAxisLabelPosition = LabelPosition.LEFT
            )
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)){
                LineGraph(
                    xAxisData = listOf("周一", "周二", "周三", "周四", "周五", "周六", "周日").map {
                        GraphData.String(it)
                    },
                    yAxisData = listOf(16.5, 24,17, 40, 10, 50, 30),
                    style = style2,
                    onPointClicked = {
                        clickedValue.value = it
                    },
                )
            }


            clickedValue.value?.let {
                Row(
                    modifier = Modifier
                        .padding(all = 25.dp)
                ) {
                    Text(text = "", color = Color.Gray)
                    Text(
                        text = "${it.first}, ${it.second}",
                        color = GraphAccent2,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}
