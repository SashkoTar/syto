package net.sparja.syto.filter.elliptic.analog

import net.sparja.syto.filter.{Approximation, Roots, TransferFunctionBuilder}
import junit.framework.TestCase.assertEquals
import org.junit.Test

class LowPassFilterTest {


  def calculateCoefficients(order: Int, ripplePass: Double, rippleStop: Double, cutOffFrequency: Double) = {
    new TransferFunctionBuilder()
      .ellipticApproximation(order, ripplePass, rippleStop)
      .transformToLowPass(cutOffFrequency)
      .coefficients
  }

  @Test
  def shouldCalculateTwoOrderFilter() = {
    val (b, a) = calculateCoefficients(2, 5, 40, 10.0)
    assertEquals(a(0), 1.0, 0.001)
    assertEquals(a(1), 4.53393674199, 0.001)
    assertEquals(a(2), 61.3535302373, 0.001)
    assertEquals(b(0), 0.0100001249734, 0.001)
    assertEquals(b(1), 0.0, 0.001)
    assertEquals(b(2), 34.5016254987, 0.001)
  }

  @Test
  def shouldCalculateThreeOrderFilter() = {
    val (b, a) = calculateCoefficients(3, 5, 40, 10.0)
    assertEquals(a(0), 1.0, 0.001)
    assertEquals(a(1), 4.22569399016, 0.001)
    assertEquals(a(2), 87.2518734531, 0.001)
    assertEquals(a(3), 195.148916216, 0.01)
    assertEquals(b(0), 0.485393536936, 0.001)
    assertEquals(b(1), 0.0, 0.001)
    assertEquals(b(2), 195.148916216, 0.1)
  }


}
