package com.eggt0r.cmcalculadora

import kotlin.math.pow

/**
 * 1. Interés Compuesto (En un periodo específico)
 * Calcula los intereses generados en un solo periodo sumando los intereses acumulados al capital.
 * @param p Principal / Capital inicial.
 * @param ia Intereses acumulados anteriormente.
 * @param r Tasa de interés por periodo.
 */
fun calcularInteresPorPeriodo(p: Double, ia: Double, r: Double): Double {
    return (p + ia) * r
}

/**
 * 2. Valor Presente de una Anualidad Ordinaria (Vencida)
 * Calcula el equivalente en dinero actual de una serie de flujos de efectivo iguales.
 * @param a Monto de la anualidad o pago periódico.
 * @param r Tasa de interés periódica (descuento).
 * @param n Número total de pagos o periodos.
 */
fun calcularValorPresenteAnualidad(a: Double, r: Double, n: Double): Double {
    // Se previene la división por cero si la tasa es 0
    if (r == 0.0) return a * n
    return a * ((1 - (1 + r).pow(-n)) / r)
}

/**
 * 3. Valuación de CETES
 * Determina el precio de compra a descuento usando la convención de 360 días.
 * @param vn Valor Nominal (Garantizado al vencimiento, usualmente $10.00).
 * @param r Tasa de rendimiento o descuento anual.
 * @param plazoDias Días transcurridos hasta el vencimiento.
 */
fun calcularPrecioCetes(vn: Double, r: Double, plazoDias: Int): Double {
    return vn / (1 + r * (plazoDias / 360.0))
}

/**
 * 4. Valuación de un Bono Cuponado (Ej. Bonos M)
 * Calcula el precio teórico sumando el valor presente de los cupones y del valor nominal al vencimiento.
 * @param vn Valor nominal del bono.
 * @param tc Tasa cupón anual.
 * @param r Tasa de rendimiento o descuento de mercado.
 * @param n Número total de cupones por liquidar.
 * @param nj Plazo en días del cupón (ej. 182 para semestral).
 */

fun calcularPrecioBonoCuponado(vn: Double, tc: Double, r: Double, n: Double, nj: Int): Double {
    // 1. Calcular el importe del flujo periódico (Cupón)
    val c = vn * ((nj * tc) / 360.0)

    // 2. Calcular el valor presente de la anualidad (todos los cupones)
    val vpCupones = if (r == 0.0) c * n else c * ((1 - (1 + r).pow(-n)) / r)

    // 3. Calcular el valor presente del pago único final (Valor Nominal)
    val vpNominal = vn / (1 + r).pow(n)

    // Precio total del bono
    return vpCupones + vpNominal
}