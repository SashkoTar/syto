package com.sparja.syto.filter.chebyshev.digital


import com.sparja.syto.filter.{Approximation, TransferFunctionBuilder}
import junit.framework.TestCase.assertEquals
import org.junit.Test

class HighPassFilterTest {

  def calculateCoefficients(order: Int, ripple: Double, cufOffFreq: Double, sampleFreq: Double) = {
    new TransferFunctionBuilder()
      .chebyshevApproximation(order, ripple)
      .digitalize(sampleFreq)
      .transformToHighPass(cufOffFreq)
      .coefficients
  }

  @Test
  def shouldCalculateFiveOrderFilter() ={

    val (b, a) = calculateCoefficients(5, 40f, 0.25f, 32)
    assertEquals(a(0), 1.0, 0.001)
    assertEquals(a(1), -3.14051920955, 0.001)
    assertEquals(a(2), 2.59049882647, 0.001)
    assertEquals(a(3), 1.08949750564, 0.001)
    assertEquals(a(4), -2.38832439515, 0.001)
    assertEquals(a(5), 0.848881567145, 0.001)
    assertEquals(b(0), 0.0751348302522, 0.001)
    assertEquals(b(1), -0.375674151261, 0.001)
    assertEquals(b(2), 0.751348302522, 0.001)
    assertEquals(b(3), -0.751348302522, 0.001)
    assertEquals(b(4), 0.375674151261, 0.001)
    assertEquals(b(5), -0.0751348302522, 0.001)

  }


}
