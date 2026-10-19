package com.eggt0r.cmcalculadora

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import java.util.Locale

private const val PREFS_AJUSTES = "ajustes"
private const val CLAVE_IDIOMA = "idioma"
private const val IDIOMA_ES = "es"
private const val IDIOMA_EN = "en"

// Se usa en MainActivity.attachBaseContext: aplica el idioma guardado (por defecto español)
fun Context.conIdiomaGuardado(): Context {
    val idioma = getSharedPreferences(PREFS_AJUSTES, Context.MODE_PRIVATE)
        .getString(CLAVE_IDIOMA, IDIOMA_ES) ?: IDIOMA_ES
    val config = Configuration(resources.configuration)
    config.setLocale(Locale.forLanguageTag(idioma))
    return createConfigurationContext(config)
}

private tailrec fun Context.buscarActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.buscarActivity()
    else -> null
}

// Botón ES / US. Su texto viene de strings.xml (R.string.boton_idioma), traducido en values-en
@Composable
fun BotonIdioma(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val idiomaActual = LocalConfiguration.current.locales[0].language

    Button(
        onClick = {
            val nuevoIdioma = if (idiomaActual == IDIOMA_ES) IDIOMA_EN else IDIOMA_ES
            context.getSharedPreferences(PREFS_AJUSTES, Context.MODE_PRIVATE)
                .edit()
                .putString(CLAVE_IDIOMA, nuevoIdioma)
                .apply()
            // Recrea la Activity para cargar los strings del nuevo idioma
            context.buscarActivity()?.recreate()
        },
        modifier = modifier
    ) {
        Text(stringResource(R.string.boton_idioma))
    }
}