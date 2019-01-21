package com.sparja.syto.filter.butterworth.analog

import junit.framework.TestCase.assertEquals
import org.junit.Test

class BandStopTransferFunctionTest {

  @Test
  def shouldCalculateTwoOrderFilter() = {
    val (b, a) = AnalogButterworthBandStopFilter(2, 10f, 20f).calculateCoefficients()
    assertEquals(a(0), 1.0, 0.001)
    assertEquals(a(1), 14.1421356237, 0.001)
    assertEquals(a(2), 500.0, 0.001)
    assertEquals(a(3), 2828.42712475, 0.001)
    assertEquals(a(4), 40000.0, 0.001)
    assertEquals(b(0), 1.0, 0.001)
    assertEquals(b(1), 0.0, 0.001)
    assertEquals(b(2), 400.0, 0.001)
    assertEquals(b(3), 0.0, 0.001)
    assertEquals(b(4), 40000.0, 0.001)
  }

  @Test
  def shouldCalculateThreeOrderFilter() = {
    val (b, a) = AnalogButterworthBandStopFilter(3, 10f, 20f).calculateCoefficients()
    assertEquals(a(0), 1.0, 0.001)
    assertEquals(a(1), 20.0, 0.001)
    assertEquals(a(2), 800.0, 0.001)
    assertEquals(a(3), 9000.0, 0.001)
    assertEquals(a(4), 160000.0, 0.001)
    assertEquals(a(5), 800000.0, 0.001)
    assertEquals(a(6), 8000000.0, 0.001)
    assertEquals(b(0), 1.0, 0.001)
    assertEquals(b(1), 0.0, 0.001)
    assertEquals(b(2), 600.0, 0.001)
    assertEquals(b(3), 0.0, 0.001)
    assertEquals(b(4), 120000.0, 0.001)
    assertEquals(b(5), 0.0, 0.001)
    assertEquals(b(6), 8000000.0, 0.001)
  }

}
