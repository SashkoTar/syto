package com.sparja.syto.filter.butterworth.analog

import org.junit.Test
import junit.framework.TestCase.assertEquals

class LowPassTransferFunctionTest {

  @Test
  def shouldCalculateNormFilter() = {
    val (b, a) = AnalogButterworthLowPassFilter(3, 1f).calculateCoefficients()
    assertEquals(a(0), 1.0, 0.001)
    assertEquals(a(1), 2.0, 0.001)
    assertEquals(a(2), 2.0, 0.001)
    assertEquals(a(3), 1.0, 0.001)
    assertEquals(b(0), 1.0, 0.001)
  }

  @Test
  def shouldCalculateFilter() = {
    val (b, a) = AnalogButterworthLowPassFilter(3, 5f).calculateCoefficients()
    assertEquals(a(0), 1.0, 0.001)
    assertEquals(a(1), 10.0, 0.001)
    assertEquals(a(2), 50.0, 0.001)
    assertEquals(a(3), 125.0, 0.001)
    assertEquals(b(0), 125.0, 0.001)
  }

}
