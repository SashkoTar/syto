package com.sparja.syto.filter.chebyshev

import org.junit.Test
import junit.framework.TestCase.{assertEquals, assertTrue}
class DigitalChebyshevFirstTypeLowPassFilterTest {

  @Test
  def shouldCalculateFiveOrderFilter() ={

    val (b, a) = DigitalChebyshevFirstTypeLowPassFilter(5, 0.001f, 0.25f, 32).calculateCoefficients()
    assertEquals(a(0), 1.0, 0.001)
    assertEquals(a(1), -4.8162048758, 0.001)
    assertEquals(a(2), 9.28438553102, 0.001)
    assertEquals(a(3), -8.95462751813, 0.001)
    assertEquals(a(4), 4.32097130207, 0.001)
    assertEquals(a(5), -0.834523366593, 0.001)
    assertEquals(b(0), 3.35177570652e-08, 0.001)
    assertEquals(b(1), 1.67588785326e-07, 0.001)
    assertEquals(b(2), 3.35177570652e-07, 0.001)
    assertEquals(b(3), 3.35177570652e-07, 0.001)
    assertEquals(b(4), 1.67588785326e-07, 0.001)
    assertEquals(b(5), 3.35177570652e-08, 0.001)

  }


}
