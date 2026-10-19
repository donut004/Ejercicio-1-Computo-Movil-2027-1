package com.eggt0r.cmcalculadora

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.eggt0r.cmcalculadora.ui.theme.GrisLimpiar
import com.eggt0r.cmcalculadora.ui.theme.VerdeCalcular
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class Pantalla {
    MENU,
    INTERES_COMPUESTO,
    ANUALIDAD,
    CETES,
    BONO
}

@Composable
fun AppCalculadoraScreen(modifier: Modifier = Modifier) {
    Box(modifier = Modifier.fillMaxSize()) {
        EnrutadorPantallas(modifier = modifier)

       // BotonIdioma(
            //modifier = Modifier
                //.align(Alignment.TopEnd)
                //.then(modifier)
               // .padding(16.dp)
       // )
    }
}

@Composable
fun EnrutadorPantallas(modifier: Modifier = Modifier) {
    var pantallaActual by rememberSaveable { mutableStateOf(Pantalla.MENU) }

    // Estructura condicional que dibuja el Composable correspondiente
    when (pantallaActual) {
        Pantalla.MENU -> MenuPrincipalScreen(
            modifier = modifier,
            onSeleccionarOperacion = { operacionSeleccionada ->
                pantallaActual = operacionSeleccionada // Cambiamos de pantalla
            }
        )
        Pantalla.INTERES_COMPUESTO -> InteresCompuestoScreen(
            modifier = modifier,
            onVolver = { pantallaActual = Pantalla.MENU } // Lambda para regresar al menú
        )
        Pantalla.ANUALIDAD -> AnualidadScreen(
            modifier = modifier,
            onVolver = { pantallaActual = Pantalla.MENU }
        )
        Pantalla.CETES -> CetesScreen(
            modifier = modifier,
            onVolver = { pantallaActual = Pantalla.MENU }
        )
        Pantalla.BONO -> BonoScreen(
            modifier = modifier,
            onVolver = { pantallaActual = Pantalla.MENU }
        )
    }
}

// 3. Vista del Menú Principal
@Composable
fun MenuPrincipalScreen(
    modifier: Modifier = Modifier,
    onSeleccionarOperacion: (Pantalla) -> Unit // Función Lambda para notificar qué eligió el usuario
) {
    Box(modifier = modifier.fillMaxSize()) {

        Image(
            painter = painterResource(id = R.drawable.bg2),
            contentDescription = stringResource(R.string.cd_fondo),
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = stringResource(R.string.menu_titulo), fontSize = 28.sp)

            Spacer(modifier = Modifier.height(32.dp)) // Espacio visual

            // Botones del menú
            Button(
                onClick = { onSeleccionarOperacion(Pantalla.INTERES_COMPUESTO) },
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            ) {
                Text(stringResource(R.string.menu_interes_compuesto))
            }

            Button(
                onClick = { onSeleccionarOperacion(Pantalla.ANUALIDAD) },
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            ) {
                Text(stringResource(R.string.menu_anualidad))
            }

            Button(
                onClick = { onSeleccionarOperacion(Pantalla.CETES) },
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            ) {
                Text(stringResource(R.string.menu_cetes))
            }

            Button(
                onClick = { onSeleccionarOperacion(Pantalla.BONO) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.menu_bono))
            }
        }
    }
}

@Composable
fun InteresCompuestoScreen(modifier: Modifier = Modifier, onVolver: () -> Unit) {
    var capitalText by rememberSaveable { mutableStateOf("") }
    var tasaText by rememberSaveable { mutableStateOf("") }
    var interesesAcumuladosText by rememberSaveable { mutableStateOf("") }
    // Guardamos el número (no el texto) para que el resultado se traduzca si cambia el idioma
    var resultado by rememberSaveable { mutableStateOf<Double?>(null) }

    val scrollState = rememberScrollState()

    Box(modifier = modifier.fillMaxSize()) {

        Image(
            painter = painterResource(id = R.drawable.bg2),
            contentDescription = stringResource(R.string.cd_fondo),
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Button(onClick = onVolver) { Text(stringResource(R.string.boton_volver)) }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text(text = stringResource(R.string.interes_titulo), fontSize = 24.sp)
            Text(text = stringResource(R.string.interes_subtitulo), fontSize = 14.sp)

            Spacer(modifier = Modifier.height(16.dp))

            Image(
                painter = painterResource(id = R.drawable.formula1),
                contentDescription = stringResource(R.string.cd_formula_interes),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // TextFields
            TextField(
                value = capitalText,
                onValueChange = { if (it.isEmpty() || (it.count { c -> c == '.' } <= 1 && it.replace(".", "").all { c -> c.isDigit() })) capitalText = it },
                label = { Text(stringResource(R.string.interes_capital)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = tasaText,
                onValueChange = { if (it.isEmpty() || (it.count { c -> c == '.' } <= 1 && it.replace(".", "").all { c -> c.isDigit() })) tasaText = it },
                label = { Text(stringResource(R.string.interes_tasa)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = interesesAcumuladosText,
                onValueChange = { if (it.isEmpty() || (it.count { c -> c == '.' } <= 1 && it.replace(".", "").all { c -> c.isDigit() })) interesesAcumuladosText = it },
                label = { Text(stringResource(R.string.interes_acumulados)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = resultado?.let { stringResource(R.string.interes_resultado, it) }
                    ?: stringResource(R.string.interes_resultado_inicial),
                fontSize = 22.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Button(
                    onClick = {
                        capitalText = ""
                        tasaText = ""
                        interesesAcumuladosText = ""
                        resultado = null
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = GrisLimpiar)
                ) {
                    Text(stringResource(R.string.boton_limpiar), fontSize = 18.sp)
                }

                Button(
                    onClick = {
                        val p = capitalText.toDoubleOrNull() ?: 0.0
                        val ia = interesesAcumuladosText.toDoubleOrNull() ?: 0.0
                        val rPorcentaje = tasaText.toDoubleOrNull() ?: 0.0
                        val r = rPorcentaje / 100.0

                        resultado = calcularInteresPorPeriodo(p, ia, r)
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = VerdeCalcular)
                ) {
                    Text(stringResource(R.string.boton_calcular), fontSize = 18.sp)
                }
            }
        }
    }
}

@Composable
fun AnualidadScreen(modifier: Modifier = Modifier, onVolver: () -> Unit) {
    var anualidadText by rememberSaveable { mutableStateOf("") }
    var tasaText by rememberSaveable { mutableStateOf("") }
    var periodosText by rememberSaveable { mutableStateOf("") }
    var resultado by rememberSaveable { mutableStateOf<Double?>(null) }

    val scrollState = rememberScrollState()

    Box(modifier = modifier.fillMaxSize()) {

        Image(
            painter = painterResource(id = R.drawable.bg2),
            contentDescription = stringResource(R.string.cd_fondo),
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(modifier = Modifier.fillMaxWidth()) { Button(onClick = onVolver) { Text(stringResource(R.string.boton_volver)) } }
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = stringResource(R.string.anualidad_titulo), fontSize = 24.sp)
            Text(text = stringResource(R.string.anualidad_subtitulo), fontSize = 14.sp)

            Spacer(modifier = Modifier.height(16.dp))

            Image(
                painter = painterResource(id = R.drawable.formula2),
                contentDescription = stringResource(R.string.cd_formula_anualidad),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            TextField(
                value = anualidadText,
                onValueChange = { if (it.isEmpty() || (it.count { c -> c == '.' } <= 1 && it.replace(".", "").all { c -> c.isDigit() })) anualidadText = it },
                label = { Text(stringResource(R.string.anualidad_monto)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = tasaText,
                onValueChange = { if (it.isEmpty() || (it.count { c -> c == '.' } <= 1 && it.replace(".", "").all { c -> c.isDigit() })) tasaText = it },
                label = { Text(stringResource(R.string.anualidad_tasa)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = periodosText,
                onValueChange = { if (it.isEmpty() || (it.count { c -> c == '.' } <= 1 && it.replace(".", "").all { c -> c.isDigit() })) periodosText = it },
                label = { Text(stringResource(R.string.anualidad_pagos)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(24.dp))

            // Muestra del resultado antes de los botones
            Text(
                text = resultado?.let { stringResource(R.string.anualidad_resultado, it) }
                    ?: stringResource(R.string.anualidad_resultado_inicial),
                fontSize = 22.sp
            )
            Spacer(modifier = Modifier.height(24.dp))

            // Fila de Botones
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Button(
                    onClick = {
                        anualidadText = ""
                        tasaText = ""
                        periodosText = ""
                        resultado = null
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = GrisLimpiar)
                ) { Text(stringResource(R.string.boton_limpiar), fontSize = 18.sp) }

                Button(
                    onClick = {
                        val a = anualidadText.toDoubleOrNull() ?: 0.0
                        val n = periodosText.toDoubleOrNull() ?: 0.0
                        val rPorcentaje = tasaText.toDoubleOrNull() ?: 0.0
                        val r = rPorcentaje / 100.0

                        resultado = calcularValorPresenteAnualidad(a, r, n)
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = VerdeCalcular)
                ) { Text(stringResource(R.string.boton_calcular), fontSize = 18.sp) }
            }
        }
    }
}

@Composable
fun CetesScreen(modifier: Modifier = Modifier, onVolver: () -> Unit) {
    var valorNominalText by rememberSaveable { mutableStateOf("") }
    var tasaText by rememberSaveable { mutableStateOf("") }
    var diasText by rememberSaveable { mutableStateOf("") }
    var resultado by rememberSaveable { mutableStateOf<Double?>(null) }

    val scrollState = rememberScrollState()

    Box(modifier = modifier.fillMaxSize()) {

        Image(
            painter = painterResource(id = R.drawable.bg2),
            contentDescription = stringResource(R.string.cd_fondo),
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(modifier = Modifier.fillMaxWidth()) { Button(onClick = onVolver) { Text(stringResource(R.string.boton_volver)) } }
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = stringResource(R.string.cetes_titulo), fontSize = 24.sp)

            Spacer(modifier = Modifier.height(16.dp))

            Image(
                painter = painterResource(id = R.drawable.formula3),
                contentDescription = stringResource(R.string.cd_formula_cetes),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            TextField(
                value = valorNominalText,
                onValueChange = { if (it.isEmpty() || (it.count { c -> c == '.' } <= 1 && it.replace(".", "").all { c -> c.isDigit() })) valorNominalText = it },
                label = { Text(stringResource(R.string.cetes_valor_nominal)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = tasaText,
                onValueChange = { if (it.isEmpty() || (it.count { c -> c == '.' } <= 1 && it.replace(".", "").all { c -> c.isDigit() })) tasaText = it },
                label = { Text(stringResource(R.string.cetes_tasa)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = diasText,
                onValueChange = { if (it.isEmpty() || (it.count { c -> c == '.' } <= 1 && it.replace(".", "").all { c -> c.isDigit() })) diasText = it },
                label = { Text(stringResource(R.string.cetes_plazo)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = resultado?.let { stringResource(R.string.cetes_resultado, it) }
                    ?: stringResource(R.string.cetes_resultado_inicial),
                fontSize = 22.sp
            )
            Spacer(modifier = Modifier.height(24.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Button(
                    onClick = {
                        valorNominalText = ""
                        tasaText = ""
                        diasText = ""
                        resultado = null
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = GrisLimpiar)
                ) { Text(stringResource(R.string.boton_limpiar), fontSize = 18.sp) }

                Button(
                    onClick = {
                        val vn = valorNominalText.toDoubleOrNull() ?: 0.0
                        val dias = diasText.toDoubleOrNull()?.toInt() ?: 0
                        val rPorcentaje = tasaText.toDoubleOrNull() ?: 0.0
                        val r = rPorcentaje / 100.0

                        resultado = calcularPrecioCetes(vn, r, dias)
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = VerdeCalcular)
                ) { Text(stringResource(R.string.boton_calcular), fontSize = 18.sp) }
            }
        }
    }
}


@Composable
fun BonoScreen(modifier: Modifier = Modifier, onVolver: () -> Unit) {
    var valorNominalText by rememberSaveable { mutableStateOf("") }
    var tasaCuponText by rememberSaveable { mutableStateOf("") }
    var tasaMercadoText by rememberSaveable { mutableStateOf("") }
    var periodosText by rememberSaveable { mutableStateOf("") }
    var diasCuponText by rememberSaveable { mutableStateOf("") }
    var resultado by rememberSaveable { mutableStateOf<Double?>(null) }

    val scrollState = rememberScrollState()

    Box(modifier = modifier.fillMaxSize()) {

        Image(
            painter = painterResource(id = R.drawable.bg2),
            contentDescription = stringResource(R.string.cd_fondo),
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(modifier = Modifier.fillMaxWidth()) { Button(onClick = onVolver) { Text(stringResource(R.string.boton_volver)) } }
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = stringResource(R.string.bono_titulo), fontSize = 24.sp)

            Spacer(modifier = Modifier.height(16.dp))

            Image(
                painter = painterResource(id = R.drawable.formula4),
                contentDescription = stringResource(R.string.cd_formula_bono),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            TextField(
                value = valorNominalText,
                onValueChange = { if (it.isEmpty() || (it.count { c -> c == '.' } <= 1 && it.replace(".", "").all { c -> c.isDigit() })) valorNominalText = it },
                label = { Text(stringResource(R.string.bono_valor_nominal)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = tasaCuponText,
                onValueChange = { if (it.isEmpty() || (it.count { c -> c == '.' } <= 1 && it.replace(".", "").all { c -> c.isDigit() })) tasaCuponText = it },
                label = { Text(stringResource(R.string.bono_tasa_cupon)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = tasaMercadoText,
                onValueChange = { if (it.isEmpty() || (it.count { c -> c == '.' } <= 1 && it.replace(".", "").all { c -> c.isDigit() })) tasaMercadoText = it },
                label = { Text(stringResource(R.string.bono_tasa_mercado)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = periodosText,
                onValueChange = { if (it.isEmpty() || (it.count { c -> c == '.' } <= 1 && it.replace(".", "").all { c -> c.isDigit() })) periodosText = it },
                label = { Text(stringResource(R.string.bono_cupones)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = diasCuponText,
                onValueChange = { if (it.isEmpty() || (it.count { c -> c == '.' } <= 1 && it.replace(".", "").all { c -> c.isDigit() })) diasCuponText = it },
                label = { Text(stringResource(R.string.bono_dias_cupon)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = resultado?.let { stringResource(R.string.bono_resultado, it) }
                    ?: stringResource(R.string.bono_resultado_inicial),
                fontSize = 22.sp
            )
            Spacer(modifier = Modifier.height(24.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Button(
                    onClick = {
                        valorNominalText = ""
                        tasaCuponText = ""
                        tasaMercadoText = ""
                        periodosText = ""
                        diasCuponText = ""
                        resultado = null
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = GrisLimpiar)
                ) { Text(stringResource(R.string.boton_limpiar), fontSize = 18.sp) }

                Button(
                    onClick = {
                        val vn = valorNominalText.toDoubleOrNull() ?: 0.0
                        val tc = (tasaCuponText.toDoubleOrNull() ?: 0.0) / 100.0
                        val r = (tasaMercadoText.toDoubleOrNull() ?: 0.0) / 100.0
                        val n = periodosText.toDoubleOrNull() ?: 0.0
                        val diasCupon = diasCuponText.toDoubleOrNull()?.toInt() ?: 0

                        resultado = calcularPrecioBonoCuponado(vn, tc, r, n, diasCupon)
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = VerdeCalcular)
                ) { Text(stringResource(R.string.boton_calcular), fontSize = 18.sp) }
            }
        }
    }
}