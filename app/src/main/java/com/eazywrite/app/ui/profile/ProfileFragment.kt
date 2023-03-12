package com.eazywrite.app.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.eazywrite.app.ui.theme.EazyWriteTheme

class ProfileFragment(private val paddingValues: PaddingValues) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(this.requireContext()).apply {
            setContent {
                EazyWriteTheme {
                    Surface(color = MaterialTheme.colorScheme.background) {
                        ProfilePage(paddingValues)
                    }
                }
            }
        }
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfilePage(paddingValues: PaddingValues) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                modifier = Modifier.padding(top = paddingValues.calculateTopPadding()),
                title = {
                    Text(text = "我的")
                }
            )
        }) {
        Box(
            modifier = Modifier.padding(it).fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "我的")
        }
    }

}