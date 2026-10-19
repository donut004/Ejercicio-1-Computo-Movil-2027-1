package com.eggt0r.cmcalculadora

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.eggt0r.cmcalculadora.ui.theme.CMCalculadoraTheme

class MainActivity : ComponentActivity() {

    // Aplica el idioma guardado (es / en) antes de crear la interfaz
    //override fun attachBaseContext(newBase: Context) {
    //    super.attachBaseContext(newBase.conIdiomaGuardado())
    //}

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CMCalculadoraTheme {
                val windowSizeClass = calculateWindowSizeClass(this)
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    when (windowSizeClass.widthSizeClass) {
                        WindowWidthSizeClass.Expanded -> {
                            AppCalculadoraScreen(modifier = Modifier.padding(innerPadding))
                        }
                        else -> {
                            AppCalculadoraScreen(modifier = Modifier.padding(innerPadding))
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun MainScreen(name: String, modifier: Modifier = Modifier) {
    Text(text = stringResource(R.string.main_pantalla_compacta), modifier = modifier)
}

@Composable
fun MainScreenNarrow(modifier: Modifier = Modifier) {
    Text(text = stringResource(R.string.main_pantalla_intermedia), modifier = modifier)
}

@Composable
fun MainScreenWide(modifier: Modifier = Modifier) {
    Text(text = stringResource(R.string.main_pantalla_ancha), modifier = modifier)
}