package com.sparja.syto.filter.chebyshev.analog

import com.sparja.syto.filter.{Approximation, TransferFunctionBuilder}
import junit.framework.TestCase.assertEquals
import org.junit.Test

class BandStopFilterTest {

  def calculateCoefficients(order: Int, ripple: Double, lowFreq: Double, highFreq: Double) = {
    new TransferFunctionBuilder()
      .chebyshevApproximation(order, ripple)
      .transformToBandStop(lowFreq, highFreq)
      .coefficients
  }

  @Test
  def shouldCalculateTwoOrderFilter() = {
    val (b, a) = calculateCoefficients(2, 10f, 10f, 30f)
    assertEquals(a(0), 1.0, 0.001)
    assertEquals(a(1), 8.82571679133, 0.001)
    assertEquals(a(2), 1358.94663844, 0.001)
    assertEquals(a(3), 2647.7150374, 0.001)
    assertEquals(a(4), 90000.0, 0.001)
    assertEquals(b(0), 0.316227766017, 0.001)
    assertEquals(b(1), 0.0, 0.001)
    assertEquals(b(2), 189.73665961, 0.001)
    assertEquals(b(3), 0.0, 0.001)
    assertEquals(b(4), 28460.4989415, 0.001)
  }

  @Test
  def shouldCalculateFiveOrderFilter() = {
    val (b, a) = calculateCoefficients(5, 10f, 10f, 30f)
    assertEquals(a(0), 1.0, 0.001)
    assertEquals(a(1), 312.126349337, 0.001)
    assertEquals(a(2), 5211.42897524, 0.001)
    assertEquals(a(3), 863187.520689, 0.001)
    assertEquals(a(4), 5869073.644, 0.001)
    assertEquals(a(5), 615329769.533, 0.001)
    assertEquals(a(6), 1760722093.2, 0.001)
    assertEquals(a(7), 7.768687686201639E10, 0.001)
    assertEquals(a(8), 1.4070858233153058E11, 0.001)
    assertEquals(a(9), 2.528223429630227E12, 0.001)
    assertEquals(a(10), 2.430000000000083E12, 0.001)
    assertEquals(b(0), 1.0, 0.001)
    assertEquals(b(1), 0.0, 0.001)
    assertEquals(b(2), 1500.0, 0.001)
    assertEquals(b(3), 0.0, 0.001)
    assertEquals(b(4), 900000.0, 0.001)
    assertEquals(b(5), 0.0, 0.001)
    assertEquals(b(6), 270000000.0, 0.001)
    assertEquals(b(7), 0.0, 0.001)
    assertEquals(b(8), 40500000000.0, 0.001)
    assertEquals(b(9), 0.0, 0.001)
    assertEquals(b(10), 2.430000000000002E12, 0.001)
  }


}
