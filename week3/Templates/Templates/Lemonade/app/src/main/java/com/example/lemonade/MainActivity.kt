package com.example.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lemonade.ui.theme.LemonadeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LemonadeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LemonApp(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun LemonApp(modifier: Modifier = Modifier) {

    var currentStep by remember { mutableIntStateOf(1) }
    var squeezeCount by remember { mutableIntStateOf(0) }

    val imageResource = when(currentStep){ 1-> R.drawable.lemon_tree
                                        2-> R.drawable.lemon_squeeze
                                        3-> R.drawable.lemon_drink
                                        else->R.drawable.lemon_restart }
    val textResource = when(currentStep){ 1-> R.string.lemon_select
                                        2-> R.string.lemon_squeeze
                                        3-> R.string.lemon_drink
                                        else->R.string.lemon_restart }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {

        Image(
            painter = painterResource(id = imageResource),
            contentDescription = null,
            modifier = Modifier.clickable {
                when (currentStep) {
                    1 -> {                       // chạm cây → sang bước vắt
                        currentStep = 2
                        squeezeCount = (9..13).random()   // đặt số lần phải vắt
                    }
                    2 -> {                       // chạm quả chanh để vắt
                        squeezeCount--
                        if (squeezeCount <= 0) currentStep = 3
                    }
                    3 -> currentStep = 4         // chạm cốc nước → sang bước restart
                    4 -> currentStep = 1         // chạm lại → quay về cây
                }
            }
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(text = stringResource(id = textResource))

    }

}

@Preview(showBackground = true)
@Composable
fun LemonAppPreview() {
    LemonadeTheme {
        LemonApp()

    }
}