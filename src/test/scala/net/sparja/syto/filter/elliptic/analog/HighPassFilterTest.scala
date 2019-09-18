package net.sparja.syto.filter.elliptic.analog

import net.sparja.syto.filter.{Approximation, Roots, TransferFunctionBuilder}
import junit.framework.TestCase.assertEquals
import org.junit.Test

class HighPassFilterTest {


  def calculateCoefficients(order: Int, ripplePass: Double, rippleStop: Double, cutOffFrequency: Double) = {
    new TransferFunctionBuilder()
      .ellipticApproximation(order, ripplePass, rippleStop)
      .transformToHighPass(cutOffFrequency)
      .coefficients
  }

  @Test
  def shouldCalculateTwoOrderFilter() = {
    val (b, a) = calculateCoefficients(2, 5, 40, 10.0)
    assertEquals(a(0), 1.0, 0.001)
    assertEquals(a(1), 7.38985470674, 0.001)
    assertEquals(a(2), 162.98980615, 0.01)
    assertEquals(b(0), 0.56234132519, 0.001)
    assertEquals(b(1), 0.0, 0.001)
    assertEquals(b(2), 1.62991843089, 0.001)
  }

  @Test
  def shouldCalculateThreeOrderFilter() = {
    val (b, a) = calculateCoefficients(3, 5, 40, 10.0)
    assertEquals(a(0), 1.0, 0.001)
    assertEquals(a(1), 44.710406363, 0.001)
    assertEquals(a(2), 216.536892549, 0.01)
    assertEquals(a(3), 5124.29184538, 0.1)
    assertEquals(b(0), 1.0, 0.001)
    assertEquals(b(1), 0.0, 0.001)
    assertEquals(b(2), 24.8729814312, 0.01)
    assertEquals(b(3), 0.0, 0.001)
  }



}
