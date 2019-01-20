package com.sparja.syto.filter.chebyshev.analog

import junit.framework.TestCase.assertEquals
import org.junit.Test

class AnalogChebyshevSecondTypeBandPassFilterTest {

  @Test
  def shouldCalculateTwoOrderFilter() = {
    val (b, a) = AnalogChebyshevSecondTypeBandPassFilter(2, 10f, 10f, 20f).calculateCoefficients()
    assertEquals(a(0), 1.0, 0.001)
    assertEquals(a(1), 9.30005948404, 0.001)
    assertEquals(a(2), 463.245553203, 0.001)
    assertEquals(a(3), 1860.01189681, 0.001)
    assertEquals(a(4), 40000.0, 0.001)
    assertEquals(b(0), 0.316227766017, 0.001)
    assertEquals(b(1), 0.0, 0.001)
    assertEquals(b(2), 189.73665961, 0.001)
    assertEquals(b(3), 0.0, 0.001)
    assertEquals(b(4), 12649.1106407, 0.001)
  }

  @Test
  def shouldCalculateThreeOrderFilter() = {
    val (b, a) = AnalogChebyshevSecondTypeBandPassFilter(3, 10f, 13f, 29f).calculateCoefficients()
    assertEquals(a(0), 1.0, 0.001)
    assertEquals(a(1), 33.6929229436, 0.001)
    assertEquals(a(2), 1570.60652824, 0.001)
    assertEquals(a(3), 30865.7972328, 0.001)
    assertEquals(a(4), 592118.661147, 0.001)
    assertEquals(a(5), 4788741.44505, 0.001)
    assertEquals(a(6), 53582633.0, 0.001)
    assertEquals(b(0), 16.0, 0.001)
    assertEquals(b(1), 0.0, 0.001)
    assertEquals(b(2), 17525.3333333, 0.001)
    assertEquals(b(3), 0.0, 0.001)
    assertEquals(b(4), 2274064.0, 0.001)
    assertEquals(b(5), 0.0, 0.001)
  }

  @Test
  def shouldCalculateForOrderFilter() = {
    val (b, a) = AnalogChebyshevSecondTypeBandPassFilter(4, 10f, 13f, 29f).calculateCoefficients()
    assertEquals(a(0), 1.0, 0.001)
    assertEquals(a(1), 43.1782719695, 0.1)
    assertEquals(a(2), 2644.98158514, 0.1)
    assertEquals(a(3), 61572.8376744, 0.1)
    assertEquals(a(4), 1875852.53818, 0.1)
    assertEquals(a(5), 23212959.8032, 0.1)
    assertEquals(a(6), 375928587.714, 0.1)
    assertEquals(a(7), 2313605500.52, 0.1)
    assertEquals(a(8), 20200652641.0, 0.1)
    assertEquals(b(0), 0.316227766017, 0.1)
    assertEquals(b(1), 0.0, 0.1)
    assertEquals(b(2), 1124.50593596, 0.1)
    assertEquals(b(3), 0.0, 0.1)
    assertEquals(b(4), 923781.626388, 0.1)
    assertEquals(b(5), 0.0, 0.1)
    assertEquals(b(6), 159824904.171, 0.1)
    assertEquals(b(7), 0.0, 0.001)
    assertEquals(b(8), 6.388007256745557E9, 0.001)
  }


}
