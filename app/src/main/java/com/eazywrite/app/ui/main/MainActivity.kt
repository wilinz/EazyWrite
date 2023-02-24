@file:OptIn(ExperimentalMaterial3Api::class)

package com.eazywrite.app.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.eazywrite.app.ui.bill.BillActivity
import com.eazywrite.app.ui.chart.ChartActivity
import com.eazywrite.app.ui.image_editing.ImageEditingActivity
import com.eazywrite.app.ui.theme.EazyWriteTheme
import com.eazywrite.app.ui.welcome.WelcomeActivity
import com.eazywrite.app.util.setWindow

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setWindow()
        setContent {
            EazyWriteTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = { Text(text = "记账") },
                            )
                        },
                        bottomBar = {
                            var currentSelected by rememberSaveable {
                                mutableStateOf(0)
                            }
                            val items = remember {
                                mutableStateListOf(
                                    Pair("主页", Icons.Default.Home),
                                    Pair("账单", Icons.Default.Menu),
                                    Pair("我的", Icons.Default.Person)
                                )
                            }
                            NavigationBar {
                                items.forEachIndexed { index, pair ->
                                    val selected = currentSelected == index
                                    NavigationBarItem(
                                        selected = selected,
                                        onClick = {
                                            if (!selected) {
                                                currentSelected = index
                                            }
                                        },
                                        label = {
                                            Text(
                                                text = pair.first,
                                                fontWeight = FontWeight.SemiBold,
                                            )
                                        },
                                        icon = {
                                            Icon(
                                                pair.second,
                                                contentDescription = pair.first,
                                            )
                                        }
                                    )
                                }
                            }

                        },
                    ) {
                        Box(Modifier.padding(it)) {
                            Column(
                                Modifier
                                    .fillMaxSize()
                                    .padding(8.dp)
                            ) {
                                ElevatedButton(onClick = { startActivity<BillActivity>() }) {
                                    Text(text = "记账页面")
                                }
                                ElevatedButton(onClick = { startActivity<ChartActivity>() }) {
                                    Text(text = "图表页面")
                                }
                                ElevatedButton(onClick = { startActivity<WelcomeActivity>() }) {
                                    Text(text = "欢迎页面")
                                }
                                ElevatedButton(onClick = { startActivity<ImageEditingActivity>() }) {
                                    Text(text = "图像增强")
                                }
                            }
                        }
                    }

                }
            }
        }
    }

    private inline fun <reified T> startActivity() {
        this.startActivity(
            Intent(
                this@MainActivity,
                T::class.java
            )
        )
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    EazyWriteTheme {
        Greeting("Android")
    }
}