package com.eazywrite.app.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import com.eazywrite.app.R
import com.eazywrite.app.ui.bill.AddBillContentActivity
import com.eazywrite.app.ui.bill.BillActivity
import com.eazywrite.app.ui.image_editing.CameraXActivity
import com.eazywrite.app.ui.theme.EazyWriteTheme
import com.eazywrite.app.util.startActivity
import com.google.android.material.datepicker.MaterialDatePicker
import com.leinardi.android.speeddial.compose.FabWithLabel
import com.leinardi.android.speeddial.compose.SpeedDial
import com.leinardi.android.speeddial.compose.SpeedDialState
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters


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
@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun HomePage(paddingValues: PaddingValues = PaddingValues(0.dp)) {

    val formatter = remember {
        DateTimeFormatter.ofPattern("yyyy???MM???dd???")
    }
    var dateTime by remember {
        val date = Instant.now()
        mutableStateOf(date)
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
                                        .setTitleText("???????????????")
                                        .setTheme(R.style.ThemeOverlay_App_DatePicker)
                                        .setSelection(dateTime.toEpochMilli())
                                        .build()
                                        .apply {
                                            addOnPositiveButtonClickListener { timestamp ->
                                                dateTime = Instant.ofEpochMilli(timestamp)
                                            }
                                        }
                                datePicker.show(context.supportFragmentManager, "date1")
                            }
                        },
                        colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.onPrimary)
                    ) {
                        var dateText by remember {
                            mutableStateOf("")
                        }
                        LaunchedEffect(key1 = dateTime, block = {
                            dateText = LocalDateTime.ofInstant(dateTime, ZoneId.systemDefault())
                                .format(formatter)
                        })
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = dateText, fontSize = 18.sp, fontWeight = FontWeight.Normal)
                            Icon(imageVector = Icons.Default.ExpandMore, contentDescription = null)
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            val context = LocalContext.current
            var speedDialState by rememberSaveable { mutableStateOf(SpeedDialState.Collapsed) }
            var overlayVisible: Boolean by rememberSaveable { mutableStateOf(speedDialState.isExpanded()) }
            SpeedDial(
                state = speedDialState,
                onFabClick = { expanded ->
                    overlayVisible = !expanded
                    speedDialState = if (expanded) SpeedDialState.Collapsed else SpeedDialState.Expanded
                },
                fabClosedContent = {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null)
                },
                fabOpenedContent = {
                    Icon(imageVector = Icons.Default.Close, contentDescription = null)
                }
            ) {
                item {
                    FabWithLabel(
                        onClick = { context.startActivity<AddBillContentActivity>() },
                        labelContent = { Text(text = "????????????") },
                        labelBorder = AssistChipDefaults.assistChipBorder(borderWidth = 0.dp, borderColor = Color.Transparent)
                    ) {
                        Icon(Icons.Default.Edit, null)
                    }
                }
                item {
                    FabWithLabel(
                        onClick = { context.startActivity<CameraXActivity>() },
                        labelContent = { Text(text = "????????????") },
                        labelBorder = AssistChipDefaults.assistChipBorder(borderWidth = 0.dp, borderColor = Color.Transparent)
                    ) {
                        Icon(Icons.Default.PhotoCamera ,null)
                    }
                }
            }
        },
        containerColor = Color.Transparent,
    ) { paddingValues1 ->
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column {
                Box {
                    Image(
                        painter = painterResource(id = R.drawable.bill_background),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                    Box(modifier = Modifier.padding(top = paddingValues1.calculateTopPadding())) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(text = "?????????", color = MaterialTheme.colorScheme.onPrimary)
                                Text(text = "???5600", color = MaterialTheme.colorScheme.onPrimary)
                            }
                            Text(text = "|", color = MaterialTheme.colorScheme.onPrimary)
                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(text = "?????????", color = MaterialTheme.colorScheme.onPrimary)
                                Text(text = "???5600", color = MaterialTheme.colorScheme.onPrimary)
                            }
                        }
                    }
                }
                val weeks = remember {

                    mutableStateListOf<Pair<Int, Int>>(
                    )
                }

                LaunchedEffect(key1 = dateTime, block = {
                    val date = LocalDate.ofEpochDay(dateTime.toEpochMilli() / 86400000)

                    // ???????????????????????????

                    // ???????????????????????????
                    val firstDayOfMonth = date.with(TemporalAdjusters.firstDayOfMonth())

                    // ??????????????????????????????

                    // ??????????????????????????????
                    val lastDayOfMonth = date.with(TemporalAdjusters.lastDayOfMonth())

                    // ??????????????????????????????????????????????????????Pair??????????????????????????????

                    // ??????????????????????????????????????????????????????Pair??????????????????????????????
                    val datesWithWeekdays: MutableList<Pair<Int, Int>> = ArrayList()
                    var currentDate: LocalDate = firstDayOfMonth
                    while (!currentDate.isAfter(lastDayOfMonth)) {
                        val dayOfWeek = currentDate.dayOfWeek
                        val weekday: Int = currentDate.dayOfWeek.value
                        val dateWithWeekday: Pair<Int, Int> =
                            Pair(currentDate.dayOfMonth, weekday)
                        datesWithWeekdays.add(dateWithWeekday)
                        currentDate = currentDate.plusDays(1)
                    }
                    weeks.apply {
                        clear()
                        addAll(datesWithWeekdays)
                    }
                })
                var itemWidth by remember { mutableStateOf(0.dp) }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    val density = LocalDensity.current
                    Card(shape = RoundedCornerShape(8.dp)) {
                        Box(modifier = Modifier
                            .padding(8.dp)
                            .wrapContentSize()
                            .onSizeChanged { intSize ->
                                itemWidth = with(density) {
                                    (intSize.width / 7f).toDp()
                                }
                            }
                        ) {

                            LazyRow(
                                modifier = Modifier.fillMaxWidth(),
                                content = {
                                    itemsIndexed(weeks) { index, item ->
                                        Box(
                                            Modifier
                                                .width(itemWidth),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                                Text(text = weekDayMap[item.second] ?: "")
                                                Text(text = item.first.toString())
                                            }

                                        }

                                    }
                                }
                            )
                        }
                    }
                }

            }
        }
    }

}

val weekDayMap = mapOf(1 to "???", 2 to "???", 3 to "???", 4 to "???", 5 to "???", 6 to "???", 7 to "???")

@Preview(showBackground = true)
@Composable
fun HomePagePreview() {
    EazyWriteTheme {
        HomePage()
    }
}