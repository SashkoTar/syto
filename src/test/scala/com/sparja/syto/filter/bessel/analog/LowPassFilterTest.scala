package com.sparja.syto.filter.bessel.analog

import com.sparja.syto.filter.core.{Prototype, Roots, TransferFunctionBuilder}
import junit.framework.TestCase.assertEquals
import org.junit.Test

class LowPassFilterTest {


  def calculateCoefficients(f: () => Roots, cutOffFrequency: Double) = {
    new TransferFunctionBuilder()
      .prototype(f)
      .transformToLowPass(cutOffFrequency)
      .coefficients
  }

  @Test
  def shouldCalculateTwoOrderFilter() = {
    val (b, a) = calculateCoefficients(() => Prototype.bessel(2), 2f)
    assertEquals(a(0), 1.0, 0.001)
    assertEquals(a(1), 3.46410161514, 0.001)
    assertEquals(a(2), 4.0, 0.001)
    assertEquals(b(0), 4.0, 0.001)
  }

  @Test
  def shouldCalculateThreeOrderFilter() = {
    val (b, a) = calculateCoefficients(() => Prototype.bessel(3), 3f)
    assertEquals(a(0), 1.0, 0.001)
    assertEquals(a(1), 7.29864239469, 0.001)
    assertEquals(a(2), 22.195908669, 0.001)
    assertEquals(a(3), 27.0, 0.001)
    assertEquals(b(0), 27.0, 0.001)
  }



}
