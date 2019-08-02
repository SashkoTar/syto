package com.sparja.syto.filter.chebyshevII.analog

import com.sparja.syto.filter.{Approximation, TransferFunctionBuilder}
import junit.framework.TestCase.assertEquals
import org.junit.Test

class BandStopFilterTest {

  def calculateCoefficients(order: Int, ripple: Double, lowFreq: Double, highFreq: Double) = {
    new TransferFunctionBuilder()
      .chebyshevIIApproximation(order, ripple)
      .transformToBandStop(lowFreq, highFreq)
      .coefficients
  }

  @Test
  def shouldCalculateTwoOrderFilter() = {
    val (b, a) = calculateCoefficients(2, 10f, 13f, 29f)
    assertEquals(a(0), 1.0, 0.001)
    assertEquals(a(1), 23.5274962757, 0.001)
    assertEquals(a(2), 1158.7715405, 0.001)
    assertEquals(a(3), 8869.86609594, 0.001)
    assertEquals(a(4), 142129.0, 0.001)
    assertEquals(b(0), 1.0, 0.001)
    assertEquals(b(1), 0.0, 0.001)
    assertEquals(b(2), 882.0, 0.001)
    assertEquals(b(3), 0.0, 0.001)
    assertEquals(b(4), 142129.0, 0.001)
  }

  @Test
  def shouldCalculateFiveOrderFilter() = {
    val (b, a) = calculateCoefficients(5, 10f, 13f, 29f)
    assertEquals(a(0), 1.0, 0.001)
    assertEquals(a(1), 19.2486504861, 0.001)
    assertEquals(a(2), 2390.25527277, 0.001)
    assertEquals(a(3), 34585.9983469, 0.001)
    assertEquals(a(4), 2043776.1594, 0.001)
    assertEquals(a(5), 20802867.8637, 0.001)
    assertEquals(a(6), 770503612.092, 0.001)
    assertEquals(a(7), 4.915673359051277E9, 0.001)
    assertEquals(a(8), 1.2807617105705539E11, 0.001)
    assertEquals(a(9), 3.8883530227795105E11, 0.001)
    assertEquals(a(10), 7.615646045657004E12, 0.001)
    assertEquals(b(0), 1.0, 0.001)
    assertEquals(b(1), 0.0, 0.001)
    assertEquals(b(2), 2205.0, 0.001)
    assertEquals(b(3), 0.0, 0.001)
    assertEquals(b(4), 1803690.0, 0.001)
    assertEquals(b(5), 0.0, 0.001)
    assertEquals(b(6), 679991130.0, 0.001)
    assertEquals(b(7), 0.0, 0.001)
    assertEquals(b(8), 1.18149705765e+11, 0.001)
    assertEquals(b(9), 0.0, 0.001)
    assertEquals(b(10), 7.615646045656987E12, 0.001)
  }



}
