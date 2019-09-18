package net.sparja.syto.filter.bessel.digital

import net.sparja.syto.filter.{Approximation, Roots, TransferFunctionBuilder}
import junit.framework.TestCase.assertEquals
import org.junit.Test

class LowPassFilterTest {

  def calculateCoefficients(order:Int, cutOffFrequency: Double, sampleFreq: Double) = {
    new TransferFunctionBuilder()
      .besselApproximation(order)
      .digitalize(sampleFreq)
      .transformToLowPass(cutOffFrequency)
      .coefficients
  }

  @Test
  def shouldCalculateTwoOrderFilter() = {
    val (b, a) = calculateCoefficients(2, 3.0, 40.0)
    assertEquals(a(0), 1.0, 0.001)
    assertEquals(a(1), -1.27910914999, 0.001)
    assertEquals(a(2), 0.435577759828, 0.001)
    assertEquals(b(0), 0.0391171524604, 0.001)
    assertEquals(b(1), 0.0782343049208, 0.001)
    assertEquals(b(2), 0.0391171524604, 0.001)
  }

  @Test
  def shouldCalculateThreeOrderFilter() = {
    val (b, a) = calculateCoefficients(3, 3.0, 40.0)
    assertEquals(a(0), 1.0, 0.001)
    assertEquals(a(1), -1.95419013462, 0.001)
    assertEquals(a(2), 1.33057052865, 0.001)
    assertEquals(a(3), -0.312761654673, 0.001)
    assertEquals(b(0), 0.00795234241951, 0.001)
    assertEquals(b(1), 0.0238570272585, 0.001)
    assertEquals(b(2), 0.0238570272585, 0.001)
    assertEquals(b(3), 0.00795234241951, 0.001)
  }



}
