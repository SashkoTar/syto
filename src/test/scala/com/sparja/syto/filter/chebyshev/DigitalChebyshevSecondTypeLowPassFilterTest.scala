package com.sparja.syto.filter.chebyshev

import junit.framework.TestCase.assertEquals
import org.junit.Test

class DigitalChebyshevSecondTypeLowPassFilterTest {

  @Test
  def shouldCalculateFiveOrderFilter() ={

    val (b, a) = DigitalChebyshevSecondTypeLowPassFilter(5, 0.001f, 0.25f, 32).calculateCoefficients()
    assertEquals(a(0), 1.0, 0.001)
    assertEquals(a(1), -3.20962921139, 0.001)
    assertEquals(a(2), 2.8667168715, 0.001)
    assertEquals(a(3), 0.674834803179, 0.001)
    assertEquals(a(4), -2.11121426671, 0.001)
    assertEquals(a(5), 0.779324806168, 0.001)
    assertEquals(b(0), 0.88958575735, 0.001)
    assertEquals(b(1), -2.66019213199, 0.001)
    assertEquals(b(2), 1.77062287602, 0.001)
    assertEquals(b(3), 1.77062287602, 0.001)
    assertEquals(b(4), -2.66019213199, 0.001)
    assertEquals(b(5), 0.88958575735, 0.001)

  }


}
