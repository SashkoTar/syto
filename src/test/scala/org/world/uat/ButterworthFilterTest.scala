package org.world.uat

import net.sparja.syto.filter.TransferFunctionBuilder
import junit.framework.TestCase.assertEquals
import org.junit.Test

class ButterworthFilterTest {


  def calculateCoefficients(order: Int, cufOffFreq: Double, sampleFreq: Double) = {
    new TransferFunctionBuilder()
      .butterworthApproximation(order)
      .digitalize(sampleFreq)
      .transformToHighPass(cufOffFreq)
      .coefficients
  }

  @Test
  def shouldCalculateButterworthHighPassCoefficients() = {
    val (b, a) = calculateCoefficients(5, 40f, 100)
    assertEquals(a(0), 1.0, 0.001)
    assertEquals(b(5), -0.00128258107896, 0.001)
  }


}
