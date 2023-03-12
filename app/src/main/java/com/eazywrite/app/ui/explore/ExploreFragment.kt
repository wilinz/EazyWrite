package com.eazywrite.app.ui.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.eazywrite.app.ui.bill.BillActivity
import com.eazywrite.app.ui.chart.ChartActivity
import com.eazywrite.app.ui.image_editing.CameraXActivity
import com.eazywrite.app.ui.image_editing.ImageEditingActivity
import com.eazywrite.app.ui.theme.EazyWriteTheme
import com.eazywrite.app.ui.welcome.WelcomeActivity
import com.eazywrite.app.util.startActivity

class ExploreFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(this.requireContext()).apply {
            setContent {
                EazyWriteTheme {
                    Column(
                        Modifier
                            .fillMaxSize()
                            .padding(8.dp)
                    ) {
                        val context = LocalContext.current
                        ElevatedButton(onClick = { context.startActivity<BillActivity>() }) {
                            Text(text = "记账页面")
                        }
                        ElevatedButton(onClick = { context.startActivity<ChartActivity>() }) {
                            Text(text = "图表页面")
                        }
                        ElevatedButton(onClick = { context.startActivity<WelcomeActivity>() }) {
                            Text(text = "欢迎页面")
                        }
                        ElevatedButton(onClick = { context.startActivity<ImageEditingActivity>() }) {
                            Text(text = "图像增强")
                        }
                        ElevatedButton(onClick = { context.startActivity<CameraXActivity>() }) {
                            Text(text = "相机预览")
                        }
                    }
                }
            }
        }
    }
}