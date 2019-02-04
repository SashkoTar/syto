package com.sparja.syto.filter.bessel.analog

import com.sparja.syto.filter.bessel.BesselMockedPrototypeRoots
import com.sparja.syto.filter.core.{Roots, TransferFunctionBuilder}
import junit.framework.TestCase.assertEquals
import org.junit.Test

class BandStopFilterTest {


  def calculateCoefficients(f: () => Roots, lowFreq: Double, highFreq: Double) = {
    new TransferFunctionBuilder()
      .prototype(f)
      .transformToBandStop(lowFreq, highFreq)
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
    assertEquals(b(0), 1.0, 0.001)
    assertEquals(b(1), 0.0, 0.001)
    assertEquals(b(2), 400.0, 0.001)
    assertEquals(b(3), 0.0, 0.001)
    assertEquals(b(4), 40000.0, 0.001)
  }

  @Test
  def shouldCalculateThreeOrderFilter() = {
    val (b, a) = calculateCoefficients(() => BesselMockedPrototypeRoots.threeOrder, 10.0, 20.0)
    assertEquals(a(0), 1.0, 0.001)
    assertEquals(a(1), 24.6621207433, 0.001)
    assertEquals(a(2), 843.288079823, 0.001)
    assertEquals(a(3), 10864.8482973, 0.001)
    assertEquals(a(4), 168657.615965, 0.001)
    assertEquals(a(5), 986484.829732, 0.001)
    assertEquals(a(6), 8000000.0, 0.001)
    assertEquals(b(0), 1.0, 0.001)
    assertEquals(b(1), 0.0, 0.001)
    assertEquals(b(2), 600.0, 0.001)
    assertEquals(b(3), 0.0, 0.001)
    assertEquals(b(4), 120000.0, 0.001)
    assertEquals(b(5), 0.0, 0.001)
    assertEquals(b(6), 8000000.0, 0.001)
  }



}
