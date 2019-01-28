package com.sparja.syto.filter.chebyshev.digital


import junit.framework.TestCase.assertEquals
import org.junit.Test

class DigitalChebyshevSecondTypeLowPassFilterTest {

  @Test
  def shouldCalculateFiveOrderFilter() ={
    val (b, a) = DigitalChebyshevSecondTypeLowPassFilter(5, 40f, 0.25f, 32).calculateCoefficients()
    assertEquals(a(0), 1.0, 0.001)
    assertEquals(a(1), -4.89449225853, 0.001)
    assertEquals(a(2), 9.58351381426, 0.001)
    assertEquals(a(3), -9.38340651458, 0.001)
    assertEquals(a(4), 4.59424435182, 0.001)
    assertEquals(a(5), -0.899859349672, 0.001)
    assertEquals(b(0), 0.00116721971362, 0.001)
    assertEquals(b(1), -0.00349042087602, 0.001)
    assertEquals(b(2), 0.00232322281375, 0.001)
    assertEquals(b(3), 0.00232322281375, 0.001)
    assertEquals(b(4), -0.00349042087602, 0.001)
    assertEquals(b(5), 0.00116721971362, 0.001)
  }

  //@Test
  def shouldCalculateTwoOrderFilter() ={
    val (b, a) = DigitalChebyshevSecondTypeLowPassFilter(2, 40f, 0.25f, 32).calculateCoefficients()
    assertEquals(a(0), 1.0, 0.001)
    assertEquals(a(1), -1.99022940919, 0.001)
    assertEquals(a(2), 0.990277385029, 0.001)
    assertEquals(b(0), 0.00996326094606, 0.001)
    assertEquals(b(1), -0.01987854605, 0.001)
    assertEquals(b(2), 0.00996326094606, 0.001)
  }


}
