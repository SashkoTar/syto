package net.sparja.syto.filter.chebyshev.analog

import net.sparja.syto.filter.{Approximation, TransferFunctionBuilder}
import junit.framework.TestCase.assertEquals
import org.junit.Test

class HighPassFilterTest {

  def calculateCoefficients(order: Int, ripple: Double, cufOffFreq: Double) = {
    new TransferFunctionBuilder()
      .chebyshevApproximation(order, ripple)
      .transformToHighPass(cufOffFreq)
      .coefficients
  }


  @Test
  def shouldCalculateThreeOrderFilter() = {
    val (b, a) = calculateCoefficients(3, 40f, 15f)
    assertEquals(a(0), 1.0, 0.001)
    assertEquals(a(1), 4499.90833042, 0.001)
    assertEquals(a(2), 599.991110617, 0.001)
    assertEquals(a(3), 1349932.49831, 0.001)
    assertEquals(b(0), 1.0, 0.001)
    assertEquals(b(1), 0.0, 0.001)
    assertEquals(b(2), 0.0, 0.001)
    assertEquals(b(3), 0.0, 0.001)
  }

  @Test
  def shouldCalculateFourOrderFilter() = {
    val (b, a) = calculateCoefficients(4, 40f, 15f)
    assertEquals(a(0), 1.0, 0.001)
    assertEquals(a(1), 0.506770900263, 0.001)
    assertEquals(a(2), 1799.94840837, 0.001)
    assertEquals(a(3), 176.383244075, 0.001)
    assertEquals(a(4), 404979.749494, 0.001)
    assertEquals(b(0), 0.01, 0.001)
    assertEquals(b(1), 0.0, 0.001)
    assertEquals(b(2), 0.0, 0.001)
    assertEquals(b(3), 0.0, 0.001)
    assertEquals(b(4), 0.0, 0.001)
  }


}
