package com.sparja.syto.filter.butterworth.analog

import com.sparja.syto.filter.{Prototype, TransferFunctionBuilder}
import junit.framework.TestCase.assertEquals
import org.junit.Test

class HighPassFilterTest {

  def calculateCoefficients(order: Int, cutOffFrequency: Double) = {
    new TransferFunctionBuilder()
      .butterworthApproximation(order)
      .transformToHighPass(cutOffFrequency)
      .coefficients
  }

  @Test
  def shouldCalculateFilter() = {

   val (b, a) = calculateCoefficients(2, 5.0)
    assertEquals(a(0), 1.0, 0.001)
    assertEquals(a(1), 7.07106781187, 0.001)
    assertEquals(a(2), 25.0, 0.001)
    assertEquals(b(0), 1.0, 0.001)
    assertEquals(b(1), 0.0, 0.001)
    assertEquals(b(2), 0.0, 0.001)
  }

  @Test
  def shouldCalculateSevenOrderFilter() = {
    val (b, a) = calculateCoefficients(7, 344.0)
    assertEquals(a(0), 1.0, 0.001)
    assertEquals(a(1), 1545.92196736, 0.1)
    assertEquals(a(2), 1194937.36458, 0.1)
    assertEquals(a(3), 5.939966753445526E8, 0.1)
    assertEquals(a(4), 2.0433485631852606E11, 0.1)
    assertEquals(a(5), 4.864301314335547E13, 0.1)
    assertEquals(a(6), 7.446973035994813E15, 0.1)
    assertEquals(a(7), 5.700449439202672E17, 0.1)
    assertEquals(b(0), 1.0, 0.001)
    assertEquals(b(1), 0.0, 0.001)
    assertEquals(b(2), 0.0, 0.001)
    assertEquals(b(3), 0.0, 0.001)
    assertEquals(b(4), 0.0, 0.001)
    assertEquals(b(5), 0.0, 0.001)
    assertEquals(b(6), 0.0, 0.001)
    assertEquals(b(7), 0.0, 0.001)
  }


}
