package com.sparja.syto.filter.bessel.digital

import com.sparja.syto.filter.bessel.BesselMockedPrototypeRoots
import com.sparja.syto.filter.core.{Roots, TransferFunctionBuilder}
import junit.framework.TestCase.assertEquals
import org.junit.Test

class BandPassFilterTest {

  def calculateCoefficients(f: () => Roots, lowFreq: Double, highFreq: Double, sampleFreq: Double) = {
    new TransferFunctionBuilder()
      .prototype(f)
      .digitalize(sampleFreq)
      .transformToBandPass(lowFreq, highFreq)
      .coefficients
  }

  @Test
  def shouldCalculateTwoOrderFilter() = {
    val (b, a) = calculateCoefficients(() => BesselMockedPrototypeRoots.twoOrder, 10.0, 20.0, 50.0)
    assertEquals(a(0), 1.0, 0.001)
    assertEquals(a(1), 0.893380868514, 0.001)
    assertEquals(a(2), 0.548354111324, 0.001)
    assertEquals(a(3), 0.203327354133, 0.001)
    assertEquals(a(4), 0.0967082226471, 0.001)
    assertEquals(b(0), 0.189451685995, 0.001)
    assertEquals(b(1), 0.0, 0.001)
    assertEquals(b(2), -0.378903371989, 0.001)
    assertEquals(b(3), 0.0, 0.001)
    assertEquals(b(4), 0.189451685995, 0.001)
  }

  @Test
  def shouldCalculateThreeOrderFilter() = {
    val (b, a) = calculateCoefficients(() => BesselMockedPrototypeRoots.threeOrder, 10.0, 20.0, 50.0)
    assertEquals(a(0), 1.0, 0.001)
    assertEquals(a(1), 1.34449421037, 0.001)
    assertEquals(a(2), 1.14476232516, 0.001)
    assertEquals(a(3), 0.682787412763, 0.001)
    assertEquals(a(4), 0.404307787274, 0.001)
    assertEquals(a(5), 0.131522864474, 0.001)
    assertEquals(a(6), 0.0338468333962, 0.001)
    assertEquals(b(0), 0.0861265569337, 0.001)
    assertEquals(b(1), 0.0, 0.001)
    assertEquals(b(2), -0.258379670801, 0.001)
    assertEquals(b(3), 0.0, 0.001)
    assertEquals(b(4), 0.258379670801, 0.001)
    assertEquals(b(5), 0.0, 0.001)
    assertEquals(b(6), -0.0861265569337, 0.001)
  }



}
