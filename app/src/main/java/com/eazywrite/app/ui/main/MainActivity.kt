@file:OptIn(ExperimentalMaterial3Api::class)

package com.eazywrite.app.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.WindowCompat
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.eazywrite.app.ui.theme.EazyWriteTheme
import com.eazywrite.app.util.setWindow


class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setWindow()
        setContent {
            EazyWriteTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var currentPage by remember {
                        mutableStateOf(0)
                    }
                    Scaffold(
                        bottomBar = {
                            BottomBar(currentPage, onSelected = { currentPage = it })
                        },
                    ) { paddingValues ->
                        Box(
                            modifier = Modifier.padding(bottom = paddingValues.calculateBottomPadding())
                        ) {

                            AndroidView(
                                modifier = Modifier.fillMaxSize(),
                                factory = { context ->
                                    ViewPager2(context).apply {
                                        adapter = ViewPage2Adapter(this@MainActivity, paddingValues)
                                        registerOnPageChangeCallback(
                                            onViewPage2ChangeCallback(
                                                onPageSelected = { index ->
                                                    currentPage = index
                                                    setSystemUI(index)
                                                }
                                            )
                                        )
                                        offscreenPageLimit = 4
                                        layoutParams = ViewGroup.LayoutParams(
                                            ViewGroup.LayoutParams.MATCH_PARENT,
                                            ViewGroup.LayoutParams.MATCH_PARENT
                                        )
                                    }
                                },
                                update = { view ->
                                    view.setCurrentItem(currentPage, true)
                                })

                        }
                    }

                }
            }
        }
    }

    fun setSystemUI(page: Int) {
        when (page) {
            0 -> setWindow(isAppearanceLightStatusBars = false)
            1 -> setWindow(isAppearanceLightStatusBars = true)
            2 -> setWindow(isAppearanceLightStatusBars = true)
            else -> setWindow(isAppearanceLightStatusBars = true)
        }
    }


    companion object {
        fun jumpMainActivity(context: Context) {
            context.startActivity(Intent(context, MainActivity::class.java))
        }
    }
}

data class BottomItem(val title: String, val icon: ImageVector, val selectedIcon: ImageVector)

@Composable
private fun BottomBar(currentSelected: Int, onSelected: (index: Int) -> Unit) {
    val items = remember {
        mutableStateListOf(
            BottomItem("首页", Icons.Outlined.Home, Icons.Default.Home),
            BottomItem("统计", Icons.Outlined.StackedLineChart, Icons.Default.BarChart),
            BottomItem("发现", Icons.Outlined.Explore, Icons.Default.Explore),
            BottomItem("我的", Icons.Outlined.Person, Icons.Default.Person)
        )
    }
    NavigationBar {
        items.forEachIndexed { index, item ->
            val selected = currentSelected == index
            NavigationBarItem(
                selected = selected,
                onClick = {
                    if (!selected) {
                        onSelected(index)
                    }
                },
                label = {
                    Text(
                        text = item.title,
                        fontWeight = FontWeight.SemiBold,
                    )
                },
                icon = {
                    Icon(
                        if (selected) item.selectedIcon else item.icon,
                        contentDescription = item.title,
                    )
                }
            )
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun BottomBarPreview() {
    EazyWriteTheme {
        BottomBar(currentSelected = 0, onSelected = {})
    }
}

fun onViewPage2ChangeCallback(
    onPageScrolled: ((
        position: Int,
        positionOffset: Float,
        positionOffsetPixels: Int
    ) -> Unit)? = null,
    onPageSelected: ((position: Int) -> Unit)? = null,
    onPageScrollStateChanged: ((state: Int) -> Unit)? = null
): ViewPager2.OnPageChangeCallback {
    return object : ViewPager2.OnPageChangeCallback() {
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            onPageScrolled?.invoke(position, positionOffset, positionOffsetPixels)
        }

        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            onPageSelected?.invoke(position)
        }

        override fun onPageScrollStateChanged(state: Int) {
            super.onPageScrollStateChanged(state)
            onPageScrollStateChanged?.invoke(state)
        }
    }
}