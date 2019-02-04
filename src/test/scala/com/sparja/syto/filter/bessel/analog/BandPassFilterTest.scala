package com.sparja.syto.filter.bessel.analog

import com.sparja.syto.filter.bessel.BesselMockedPrototypeRoots
import com.sparja.syto.filter.core.{Roots, TransferFunctionBuilder}
import junit.framework.TestCase.assertEquals
import org.junit.Test

class BandPassFilterTest {


  def calculateCoefficients(f: () => Roots, lowFreq: Double, highFreq: Double) = {
    new TransferFunctionBuilder()
      .prototype(f)
      .transformToBandPass(lowFreq, highFreq)
      .coefficients
  }

  @Test
  def shouldCalculateTwoOrderFilter() = {
    val (b, a) = calculateCoefficients(() => BesselMockedPrototypeRoots.twoOrder, 10.0, 20.0)
    assertEquals(a(0), 1.0, 0.001)
    assertEquals(a(1), 17.3205080757, 0.001)
    assertEquals(a(2), 500.0, 0.001)
    assertEquals(a(3), 3464.10161514, 0.001)
    assertEquals(a(4), 40000.0, 0.001)
    assertEquals(b(0), 100.0, 0.001)
    assertEquals(b(1), 0.0, 0.001)
    assertEquals(b(2), 0.0, 0.001)
  }

  @Test
  def shouldCalculateThreeOrderFilter() = {
    val (b, a) = calculateCoefficients(() => BesselMockedPrototypeRoots.threeOrder, 10.0, 20.0)
    assertEquals(a(0), 1.0, 0.001)
    assertEquals(a(1), 24.3288079823, 0.001)
    assertEquals(a(2), 846.621207433, 0.001)
    assertEquals(a(3), 10731.5231929, 0.001)
    assertEquals(a(4), 169324.241487, 0.001)
    assertEquals(a(5), 973152.319292, 0.001)
    assertEquals(a(6), 8000000.0, 0.001)
    assertEquals(b(0), 1000.0, 0.001)
    assertEquals(b(1), 0.0, 0.001)
    assertEquals(b(2), 0.0, 0.001)
    assertEquals(b(3), 0.0, 0.001)
  }



}
