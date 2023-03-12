package com.eazywrite.app.ui.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.eazywrite.app.ui.bill.BillActivity
import com.eazywrite.app.ui.image_editing.CameraXActivity
import com.eazywrite.app.ui.image_editing.ImageEditingActivity
import com.eazywrite.app.ui.theme.EazyWriteTheme
import com.eazywrite.app.ui.welcome.WelcomeActivity
import com.eazywrite.app.util.startActivity

class ExploreFragment(private val paddingValues: PaddingValues) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(this.requireContext()).apply {
            setContent {
                EazyWriteTheme {
                    ExplorePage(paddingValues)
                }
            }
        }
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExplorePage(paddingValues: PaddingValues) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                modifier = Modifier.padding(top = paddingValues.calculateTopPadding()),
                title = {
                    Text(text = "发现")
                }
            )
        }) {
        Column(
            Modifier
                .padding(it)
                .fillMaxSize()
                .padding(8.dp)
        ) {
            val context = LocalContext.current
            ElevatedButton(onClick = { context.startActivity<BillActivity>() }) {
                Text(text = "账单页面")
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