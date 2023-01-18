package com.eazywrite.app

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eazywrite.app.ui.theme.EazyWriteTheme
import com.eazywrite.part1.ui.Part1Activity
import com.eazywrite.part2.Part2Activity
import com.eazywrite.part3.Part3Activity
import com.eazywrite.part4.Part4Activity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EazyWriteTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        Modifier
                            .fillMaxSize()
                            .padding(8.dp)
                    ) {
                        ElevatedButton(onClick = {
                            startActivity<Part1Activity>()
                        }, Modifier.fillMaxWidth()) {
                            Text(text = "part1: jxy test activity")
                        }
                        ElevatedButton(onClick = {
                            startActivity<Part2Activity>()
                        }, Modifier.fillMaxWidth()) {
                            Text(text = "part2: wkj test activity")
                        }
                        ElevatedButton(onClick = {
                            startActivity<Part3Activity>()
                        }, Modifier.fillMaxWidth()) {
                            Text(text = "part3: hzw test activity")
                        }
                        ElevatedButton(onClick = {
                            startActivity<Part4Activity>()
                        }, Modifier.fillMaxWidth()) {
                            Text(text = "part4: wlz test activity")
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