package com.sparja.syto.filter.butterworth

import com.sparja.syto.filter.core.LowPassTransferFunction
import junit.framework.TestCase.assertEquals
import org.junit.Test

class DigitalButterLowPassFilter2Test {

  @Test
  def shouldCalculateButterworthLowpassCoefficients() = {
    val butterworth = new ButterworthApproximation(2)
    val filter = new LowPassTransferFunction(butterworth, 2, 3.667f, 30)
    val (b, a) = filter.calculateCoefficients()

    //Generated code
    assertEquals(a(0), 1.0, 0.1)
    assertEquals(a(1), -0.96469328157, 0.1)
    assertEquals(a(2), 0.341171898656, 0.1)
    assertEquals(b(0), 0.0941196542717, 0.1)
    assertEquals(b(1), 0.188239308543, 0.1)
    assertEquals(b(2), 0.0941196542717, 0.1)
  }
  @Test
  def shouldCalculateButterworthLowpassCoefficients2() = {

    val filter = DigitalButterworthLowPassFilter(3, 3.667f, 30)
    val (b, a) = filter.calculateCoefficients()
    //Generated code
    assertEquals(a(0), 1.0, 0.1)
    assertEquals(a(1), -1.49214284242, 0.1)
    assertEquals(a(2), 0.937562254528, 0.1)
    assertEquals(a(3), -0.205591640807, 0.1)
    assertEquals(b(0), 0.0299784714131, 0.1)
    assertEquals(b(1), 0.0899354142393, 0.1)
    assertEquals(b(2), 0.0899354142393, 0.1)
    assertEquals(b(3), 0.0299784714131, 0.1)
  }

  @Test
  def shouldCalculateButterworthLowpassCoefficients3() = {

    val filter = DigitalButterworthLowPassFilter(4, 17.22f, 50)
    val (b, a) = filter.calculateCoefficients()
    //Generated code
    assertEquals(a(0), 1.0, 0.1)
    assertEquals(a(1), 1.48163341813, 0.1)
    assertEquals(a(2), 1.18787479661, 0.1)
    assertEquals(a(3), 0.44044793321, 0.1)
    assertEquals(a(4), 0.0686427188981, 0.1)
    assertEquals(b(0), 0.261162429178, 0.1)
    assertEquals(b(1), 1.04464971671, 0.1)
    assertEquals(b(2), 1.56697457507, 0.1)
    assertEquals(b(3), 1.04464971671, 0.1)
    assertEquals(b(4), 0.261162429178, 0.1)
  }

  @Test
  def shouldCalculateButterworthLowpassCoefficients4() = {
    val (b, a) = DigitalButterworthLowPassFilter(3, 10f, 40).calculateCoefficients()
    assertEquals(a(0), 1.0, 0.001)
    assertEquals(a(1), -2.77555756156e-16, 0.00001)
    assertEquals(a(2), 0.333333333333, 0.00001)
    assertEquals(a(3), -1.85037170771e-17, 0.00001)
    assertEquals(b(0), 0.166666666667, 0.00001)
    assertEquals(b(1), 0.5, 0.00001)
    assertEquals(b(2), 0.5, 0.00001)
    assertEquals(b(3), 0.166666666667, 0.00001)
  }




}
