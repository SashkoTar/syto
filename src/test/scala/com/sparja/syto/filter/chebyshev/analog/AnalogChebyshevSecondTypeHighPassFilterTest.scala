package com.sparja.syto.filter.chebyshev.analog

import junit.framework.TestCase.assertEquals
import org.junit.Test

class AnalogChebyshevSecondTypeHighPassFilterTest {
 /*
 z, p, k = cheby2(2, 20, 1, btype='low', analog=True, output='zpk')
 zeros: [ 0.-1.41421356j -0.+1.41421356j]
poles: [-0.3-0.33166248j -0.3+0.33166248j]

z, p, k = cheby2(2, 20, 1, btype='high', analog=True, output='zpk')
zeros: [-0.+0.70710678j  0.-0.70710678j]
poles: [-1.5+1.6583124j -1.5-1.6583124j]

z, p, k = cheby2(2, 20, 5, btype='high', analog=True, output='zpk')
zeros: [-0.+3.53553391j  0.-3.53553391j]
poles: [-7.5+8.29156198j -7.5-8.29156198j]
  */


  def shouldCalculateThreeOrderFilter() = {
    val (b, a) = AnalogChebyshevSecondTypeHighPassFilter(3, 10f, 100f).calculateCoefficients()
    assertEquals(a(0), 1.0, 0.001)
    assertEquals(a(1), 128.79097507, 0.001)
    assertEquals(a(2), 15793.5576298, 0.001)
    assertEquals(a(3), 750000.0, 0.001)
    assertEquals(b(0), 1.0, 0.001)
    assertEquals(b(1), 0.0, 0.001)
    assertEquals(b(2), 7500.0, 0.001)
    assertEquals(b(3), 0.0, 0.001)
  }

  @Test
  def shouldCalculateTwoOrderFilter() = {
    val (b, a) = AnalogChebyshevSecondTypeHighPassFilter(2, 20f, 5f).calculateCoefficients()
    assertEquals(a(0), 1.0, 0.001)
    assertEquals(a(1), 15.0, 0.001)
    assertEquals(a(2), 125.0, 0.001)
    assertEquals(b(0), 1.0, 0.001)
    assertEquals(b(1), 0.0, 0.001)
    assertEquals(b(2), 12.5, 0.001)
  }

  @Test
  def shouldCalculateTwoOrderFilter2() = {
    val (b, a) = AnalogChebyshevSecondTypeHighPassFilter(2, 20f, 7f).calculateCoefficients()
    assertEquals(a(0), 1.0, 0.001)
    assertEquals(a(1), 21.0, 0.001)
    assertEquals(a(2), 245.0, 0.001)
    assertEquals(b(0), 1.0, 0.001)
    assertEquals(b(1), 0.0, 0.001)
    assertEquals(b(2), 24.5, 0.001)
  }

  @Test
  def shouldCalculateThreeOrderFilter2() = {
    val (b, a) = AnalogChebyshevSecondTypeHighPassFilter(4, 20f, 230f).calculateCoefficients()
    assertEquals(a(0), 1.0, 0.001)
    assertEquals(a(1), 492.910283576, 0.001)
    assertEquals(a(2), 174380.273827, 0.1)
    assertEquals(a(3), 34394182.3998, 0.1)
    assertEquals(a(4), 3498012500.0, 0.1)
    assertEquals(b(0), 1.0, 0.001)
    assertEquals(b(1), 0.0, 0.001)
    assertEquals(b(2), 52900.0, 0.001)
    assertEquals(b(3), 0.0, 0.001)
    assertEquals(b(4), 349801250.0, 0.001)
  }


  @Test
  def shouldCalculateTwoOrderFilter3() = {
    val (b, a) = AnalogChebyshevSecondTypeHighPassFilter(2, 20, 7).calculateCoefficients()
    assertEquals(a(0), 1.0, 0.001)
    assertEquals(a(1), 21.0, 0.001)
    assertEquals(a(2), 245.0, 0.001)
    assertEquals(b(0), 1.0, 0.001)
    assertEquals(b(1), 0.0, 0.001)
    assertEquals(b(2), 24.5, 0.001)
  }


}
