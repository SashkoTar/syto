package com.sparja.syto.filter.chebyshev

import junit.framework.TestCase.assertEquals
import org.junit.Test

class DigitalChebyshevFirstTypeHighPassFilterTest {

  @Test
  def shouldCalculateFiveOrderFilter() ={

    val (b, a) = DigitalChebyshevFirstTypeHighPassFilter(5, 0.001f, 0.25f, 32).calculateCoefficients()
    assertEquals(a(0), 1.0, 0.001)
    assertEquals(a(1), -4.88613006662, 0.001)
    assertEquals(a(2), 9.55097484874, 0.001)
    assertEquals(a(3), -9.3359158663, 0.001)
    assertEquals(a(4), 4.56343252838, 0.001)
    assertEquals(a(5), -0.892361378777, 0.001)
    assertEquals(b(0), 0.944650459026, 0.001)
    assertEquals(b(1), -4.72325229513, 0.001)
    assertEquals(b(2), 9.44650459026, 0.001)
    assertEquals(b(3), -9.44650459026, 0.001)
    assertEquals(b(4), 4.72325229513, 0.001)
    assertEquals(b(5), -0.944650459026, 0.001)

  }


}
