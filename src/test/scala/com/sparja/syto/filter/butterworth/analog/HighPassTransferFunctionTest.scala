package com.sparja.syto.filter.butterworth.analog

import junit.framework.TestCase.assertEquals
import org.junit.Test

class HighPassTransferFunctionTest {

  @Test
  def shouldCalculateFilter() = {
    val (b, a) = AnalogButterworthHighPassFilter(2, 5f).calculateCoefficients()
    assertEquals(a(0), 1.0, 0.001)
    assertEquals(a(1), 7.07106781187, 0.001)
    assertEquals(a(2), 25.0, 0.001)
    assertEquals(b(0), 1.0, 0.001)
    assertEquals(b(1), 0.0, 0.001)
    assertEquals(b(2), 0.0, 0.001)
  }

  //@Test
  def shouldCalculateSevenOrderFilter() = {
    val (b, a) = AnalogButterworthHighPassFilter(7, 344f).calculateCoefficients()
    assertEquals(a(0), 1.0, 0.001)
    assertEquals(a(1), 1545.92196736, 0.1)
    assertEquals(a(2), 1194937.36458, 0.1)
    assertEquals(a(3), 593996675.5, 0.1)
    assertEquals(a(4), 2.04334856319e+11, 0.1)
    assertEquals(a(5), 4.86430131434e+13, 0.1)
    assertEquals(a(6), 7.44697303599e+15, 0.1)
    assertEquals(a(7), 5.7004494392e+17, 0.1)
    assertEquals(b(0), 1.0, 0.001)
    assertEquals(b(1), 0.0, 0.001)
    assertEquals(b(2), 0.0, 0.001)
    assertEquals(b(3), 0.0, 0.001)
    assertEquals(b(4), 0.0, 0.001)
    assertEquals(b(5), 0.0, 0.001)
    assertEquals(b(6), 0.0, 0.001)
    assertEquals(b(7), 0.0, 0.001)
  }


}
