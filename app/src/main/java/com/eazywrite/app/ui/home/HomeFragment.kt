package com.eazywrite.app.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.More
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.eazywrite.app.R
import com.eazywrite.app.ui.theme.EazyWriteTheme
import com.google.android.material.datepicker.MaterialDatePicker
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class HomeFragment(private val paddingValues: PaddingValues) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(this.requireContext()).apply {
            setContent {
                EazyWriteTheme {
                    Surface(color = MaterialTheme.colorScheme.background) {
                        HomePage(paddingValues)
                    }
                }
            }
        }
    }


}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(paddingValues: PaddingValues = PaddingValues(0.dp)) {

    val formatter = remember {
        DateTimeFormatter.ofPattern("yyyy年MM月dd日")
    }
    var dateTime by remember {
        val date = LocalDateTime.now()
        mutableStateOf(date.format(formatter))
    }
    val context = LocalContext.current
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .padding(top = paddingValues.calculateTopPadding()),
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
                title = {
                    TextButton(
                        onClick = {
                            if (context is AppCompatActivity) {
                                val datePicker =
                                    MaterialDatePicker.Builder.datePicker()
                                        .setTitleText("请选择日期")
                                        .setTheme(R.style.ThemeOverlay_App_DatePicker)
                                        .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                                        .build()
                                        .apply {
                                            addOnPositiveButtonClickListener { timestamp ->
                                                dateTime = LocalDateTime.ofInstant(
                                                    Instant.ofEpochMilli(timestamp),
                                                    ZoneId.systemDefault()
                                                ).format(formatter)
                                            }
                                        }
                                datePicker.show(context.supportFragmentManager, "date1")
                            }
                        },
                        colors = ButtonDefaults.textButtonColors(contentColor = Color.White)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = dateTime, fontSize = 18.sp, fontWeight = FontWeight.Normal)
                            Icon(imageVector = Icons.Default.ExpandMore, contentDescription = null)
                        }
                    }
                }
            )
        },
        containerColor = Color.Transparent,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box {
                Image(
                    painter = painterResource(id = R.drawable.bill_background),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
                Box(modifier = Modifier.padding(vertical = 32.dp, horizontal = 16.dp)) {

                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun HomePagePreview() {
    EazyWriteTheme {
        HomePage()
    }
}