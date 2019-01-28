package com.sparja.syto.filter.butterworth.digital

import junit.framework.TestCase.assertEquals
import org.junit.Test

class DigitalButterHighPassFilterTest {
  @Test
  def shouldCalculateButterworthHighPassCoefficients() = {
    val (b, a) = DigitalButterworthHighPassFilter(5, 40f, 100).calculateCoefficients()
    assertEquals(a(0), 1.0, 0.001)
    assertEquals(a(1), 2.97542210975, 0.001)
    assertEquals(a(2), 3.80601811932, 0.001)
    assertEquals(a(3), 2.54525286833, 0.001)
    assertEquals(a(4), 0.881130075438, 0.001)
    assertEquals(a(5), 0.125430622155, 0.001)
    assertEquals(b(0), 0.00128258107896, 0.001)
    assertEquals(b(1), -0.0064129053948, 0.001)
    assertEquals(b(2), 0.0128258107896, 0.001)
    assertEquals(b(3), -0.0128258107896, 0.001)
    assertEquals(b(4), 0.0064129053948, 0.001)
    assertEquals(b(5), -0.00128258107896, 0.001)
  }

  @Test
  def shouldCalculateButterworthHighPassCoefficients2() = {
    val (b, a) = DigitalButterworthHighPassFilter(4, 13f, 33).calculateCoefficients()
    assertEquals(a(0), 1.0, 0.001)
    assertEquals(a(1), 2.27199298713, 0.001)
    assertEquals(a(2), 2.16254707908, 0.001)
    assertEquals(a(3), 0.964851991792, 0.001)
    assertEquals(a(4), 0.16854153741, 0.001)
    assertEquals(b(0), 0.00589022734776, 0.001)
    assertEquals(b(1), -0.023560909391, 0.001)
    assertEquals(b(2), 0.0353413640866, 0.001)
    assertEquals(b(3), -0.023560909391, 0.001)
    assertEquals(b(4), 0.00589022734776, 0.001)
  }

}
