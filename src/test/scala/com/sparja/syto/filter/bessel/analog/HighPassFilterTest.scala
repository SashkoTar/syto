package com.sparja.syto.filter.bessel.analog

import com.sparja.syto.filter.{Approximation, Roots, TransferFunctionBuilder}
import junit.framework.TestCase.assertEquals
import org.junit.Test

class HighPassFilterTest {


  def calculateCoefficients(order: Int, cutOffFrequency: Double) = {
    new TransferFunctionBuilder()
      .besselApproximation(order)
      .transformToHighPass(cutOffFrequency)
      .coefficients
  }

  @Test
  def shouldCalculateTwoOrderFilter() = {
    val (b, a) = calculateCoefficients(2, 10.0)
    assertEquals(a(0), 1.0, 0.001)
    assertEquals(a(1), 17.3205080757, 0.001)
    assertEquals(a(2), 100.0, 0.01)
    assertEquals(b(0), 1.0, 0.001)
    assertEquals(b(1), 0.0, 0.001)
    assertEquals(b(2), 0.0, 0.001)
  }

  @Test
  def shouldCalculateThreeOrderFilter() = {
    val (b, a) = calculateCoefficients(3, 10.0)
    assertEquals(a(0), 1.0, 0.001)
    assertEquals(a(1), 24.6621207433, 0.001)
    assertEquals(a(2), 243.288079823, 0.001)
    assertEquals(a(3), 1000.0, 0.001)
    assertEquals(b(0), 1.0, 0.001)
    assertEquals(b(1), 0.0, 0.001)
    assertEquals(b(2), 0.0, 0.001)
    assertEquals(b(3), 0.0, 0.001)
  }



}
