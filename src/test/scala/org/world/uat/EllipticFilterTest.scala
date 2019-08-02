package org.world.uat

import com.sparja.syto.filter.TransferFunctionBuilder
import junit.framework.TestCase.assertEquals
import org.junit.Test


class EllipticFilterTest {


  def calculateCoefficients(order: Int, rp: Double, rs: Double, lowFreq: Double, highFreq: Double, sampleFreq: Double) = {
    new TransferFunctionBuilder()
      .ellipticApproximation(order, rp, rs)
      .digitalize(sampleFreq)
      .transformToBandStop(lowFreq, highFreq)
      .coefficients
  }

  @Test
  def shouldCalculateTwoOrderFilter() = {
    val (b, a) = calculateCoefficients(2, 5, 40, 10.0, 20.0, 50.0)
    assertEquals(a(0), 1.0, 0.001)
    assertEquals(a(1), 0.808429310027, 0.001)
    assertEquals(b(4), 0.238164798819, 0.001)
  }

  @Test
  def shouldCalculateThreeOrderFilter() = {
    val (b, a) = calculateCoefficients(3, 5, 40, 10.0, 20.0, 50.0)
    assertEquals(a(0), 1.0, 0.001)
    assertEquals(a(1), 1.10486180585, 0.001)
    assertEquals(b(6), 0.153778252952, 0.001)

  }


}
