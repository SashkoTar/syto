package com.sparja.syto.filter.butterworth.analog

import junit.framework.TestCase.assertEquals
import org.junit.Test

class BandPassTransferFunctionTest {

  @Test
  def shouldCalculateFilter() = {
    val (b, a) = AnalogButterworthBandPassFilter(2, 10f, 20f).calculateCoefficients()
    assertEquals(a(0), 1.0, 0.001)
    assertEquals(a(1), 14.1421356237, 0.001)
    assertEquals(a(2), 500.0, 0.001)
    assertEquals(a(3), 2828.42712475, 0.001)
    assertEquals(a(4), 40000.0, 0.001)
    assertEquals(b(0), 100.0, 0.001)
    assertEquals(b(1), 0.0, 0.001)
    assertEquals(b(2), 0.0, 0.001)
  }

}
