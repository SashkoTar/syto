package net.sparja.syto.filter.butterworth.analog

import net.sparja.syto.filter.{Approximation, TransferFunctionBuilder}
import junit.framework.TestCase.assertEquals
import org.junit.Test

class LowPassFilterTest {

  def calculateCoefficients(order: Int, cutOffFrequency: Double) = {
    new TransferFunctionBuilder()
      .butterworthApproximation(order)
      .transformToLowPass(cutOffFrequency)
      .coefficients
  }

  @Test
  def shouldCalculateNormFilter() = {
    val (b, a) = calculateCoefficients(3, 1)
    assertEquals(a(0), 1.0, 0.001)
    assertEquals(a(1), 2.0, 0.001)
    assertEquals(a(2), 2.0, 0.001)
    assertEquals(a(3), 1.0, 0.001)
    assertEquals(b(0), 1.0, 0.001)
  }

  @Test
  def shouldCalculateFilter() = {
    val (b, a) = calculateCoefficients(3, 5)
    assertEquals(a(0), 1.0, 0.001)
    assertEquals(a(1), 10.0, 0.001)
    assertEquals(a(2), 50.0, 0.001)
    assertEquals(a(3), 125.0, 0.001)
    assertEquals(b(0), 125.0, 0.001)
  }

}
