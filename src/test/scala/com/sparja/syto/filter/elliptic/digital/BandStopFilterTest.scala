package com.sparja.syto.filter.elliptic.digital

import com.sparja.syto.filter.core.{Roots, TransferFunctionBuilder}
import com.sparja.syto.filter.elliptic.EllipticMockedPrototypeRoots
import junit.framework.TestCase.assertEquals
import org.junit.Test

class BandStopFilterTest {


  def calculateCoefficients(f: () => Roots, lowFreq: Double, highFreq: Double, sampleFreq: Double) = {
    new TransferFunctionBuilder()
      .prototype(f)
        .digitalize(sampleFreq)
      .transformToBandStop(lowFreq, highFreq)
      .coefficients
  }

  @Test
  def shouldCalculateTwoOrderFilter() = {
    val (b, a) = calculateCoefficients(() => EllipticMockedPrototypeRoots.twoOrder, 10.0, 20.0, 50.0)
    assertEquals(a(0), 1.0, 0.001)
    assertEquals(a(1), 0.808429310027, 0.001)
    assertEquals(a(2), 0.359935820127, 0.001)
    assertEquals(a(3), 0.46624121984, 0.001)
    assertEquals(a(4), 0.552069974673, 0.001)
    assertEquals(b(0), 0.238164798819, 0.001)
    assertEquals(b(1), 0.358399957473, 0.001)
    assertEquals(b(2), 0.598870274782, 0.001)
    assertEquals(b(3), 0.358399957473, 0.001)
    assertEquals(b(4), 0.238164798819, 0.001)
  }

  @Test
  def shouldCalculateThreeOrderFilter() = {
    val (b, a) = calculateCoefficients(() => EllipticMockedPrototypeRoots.threeOrder, 10.0, 20.0, 50.0)
    assertEquals(a(0), 1.0, 0.001)
    assertEquals(a(1), 1.10486180585, 0.001)
    assertEquals(a(2), 0.388240957997, 0.001)
    assertEquals(a(3), 0.44626686439, 0.001)
    assertEquals(a(4), 0.592571575664, 0.001)
    assertEquals(a(5), -0.244421718556, 0.001)
    assertEquals(a(6), -0.417393730291, 0.001)
    assertEquals(b(0), 0.153778252952, 0.001)
    assertEquals(b(1), 0.325160382469, 0.001)
    assertEquals(b(2), 0.627931148734, 0.001)
    assertEquals(b(3), 0.656386186742, 0.001)
    assertEquals(b(4), 0.627931148734, 0.001)
    assertEquals(b(5), 0.325160382469, 0.001)
    assertEquals(b(6), 0.153778252952, 0.001)
  }



}
