package org.world.uat

import com.sparja.syto.filter.{Approximation, TransferFunctionBuilder}
import junit.framework.TestCase.assertEquals
import org.junit.Test

class ChebyshevFilterTest {

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
    assertEquals(b(5), -0.0751348302522, 0.001)

  }


}
