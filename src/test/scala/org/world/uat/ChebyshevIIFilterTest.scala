package org.world.uat

import net.sparja.syto.filter.{Approximation, TransferFunctionBuilder}
import junit.framework.TestCase.assertEquals
import org.junit.Test

class ChebyshevIIFilterTest {

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
    assertEquals(b(4), 142129.0, 0.001)
  }




}
