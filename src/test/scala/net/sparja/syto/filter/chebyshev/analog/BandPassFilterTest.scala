package net.sparja.syto.filter.chebyshev.analog

import net.sparja.syto.filter.{Approximation, TransferFunctionBuilder}
import junit.framework.TestCase.assertEquals
import org.junit.Test

class BandPassFilterTest {

  def calculateCoefficients(order: Int, ripple: Double, lowFreq: Double, highFreq: Double) = {
    new TransferFunctionBuilder()
      .chebyshevApproximation(order, ripple)
      .transformToBandPass(lowFreq, highFreq)
      .coefficients
  }


  @Test
  def shouldCalculateTwoOrderFilter() = {
    val (b, a) = calculateCoefficients(2, 10f, 10f, 30f)
    assertEquals(a(0), 1.0, 0.001)
    assertEquals(a(1), 4.65156117403, 0.001)
    assertEquals(a(2), 810.818510678, 0.001)
    assertEquals(a(3), 1395.46835221, 0.001)
    assertEquals(a(4), 90000.0, 0.001)
    assertEquals(b(0), 66.6666666667, 0.001)
    assertEquals(b(1), 0.0, 0.001)
    assertEquals(b(2), 0.0, 0.001)
  }

  @Test
  def shouldCalculateFiveOrderFilter() = {
    val (b, a) = calculateCoefficients(5, 10f, 10f, 30f)
    assertEquals(a(0), 1.0, 0.001)
    assertEquals(a(1), 4.24163428719, 0.001)
    assertEquals(a(2), 2008.99573071, 0.001)
    assertEquals(a(3), 6636.38988431, 0.001)
    assertEquals(a(4), 1410117.21586, 0.001)
    assertEquals(a(5), 3285006.42556, 0.001)
    assertEquals(a(6), 423035164.759, 0.001)
    assertEquals(a(7), 597275089.588, 0.001)
    assertEquals(a(8), 5.42428847292542E10, 0.001)
    assertEquals(a(9), 3.4357237726209473E10, 0.001)
    assertEquals(a(10), 2.43e+12, 0.001)
    assertEquals(b(0), 66666.6666667, 0.001)
    assertEquals(b(1), 0.0, 0.001)
    assertEquals(b(2), 0.0, 0.001)
    assertEquals(b(3), 0.0, 0.001)
    assertEquals(b(4), 0.0, 0.001)
    assertEquals(b(5), 0.0, 0.001)
  }


}
