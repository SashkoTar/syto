package com.sparja.syto.filter.butterworth.digital

import com.sparja.syto.filter.{Prototype, TransferFunctionBuilder}
import junit.framework.TestCase.assertEquals
import org.junit.Test

class BandPassFilterTest {

  def calculateCoefficients(order: Int, lowFreq: Double, highFreq: Double, sampleFreq: Double) = {
    new TransferFunctionBuilder()
      .butterworthApproximation(order)
      .digitalize(sampleFreq)
      .transformToBandPass(lowFreq, highFreq)
      .coefficients
  }

  @Test
  def shouldCalculateButterworthBandPassCoefficients() = {

    val (b, a) = calculateCoefficients(2, 18f, 22f, 100)
    assertEquals(a(0), 1.0, 0.1)
    assertEquals(a(1), -1.13608549391, 0.1)
    assertEquals(a(2), 1.97230236061, 0.1)
    assertEquals(a(3), -0.9497603088, 0.1)
    assertEquals(a(4), 0.700896781188, 0.1)
    assertEquals(b(0), 0.0133592000279, 0.1)
    assertEquals(b(1), 0.0, 0.1)
    assertEquals(b(2), -0.0267184000557, 0.1)
    assertEquals(b(3), 0.0, 0.1)
    assertEquals(b(4), 0.0133592000279, 0.1)
  }

  @Test
  def shouldCalculateButterworthBandPassCoefficients2() = {

    //Generated code
    val (b, a) = calculateCoefficients(3, 40f, 170f, 1000)
    assertEquals(a(0), 1.0, 0.001)
    assertEquals(a(1), -3.78765084328, 0.001)
    assertEquals(a(2), 6.33731910201, 0.001)
    assertEquals(a(3), -6.09527805453, 0.001)
    assertEquals(a(4), 3.58979929087, 0.001)
    assertEquals(a(5), -1.21933151218, 0.001)
    assertEquals(a(6), 0.184405535266, 0.001)
    assertEquals(b(0), 0.0349136036115, 0.001)
    assertEquals(b(1), 0.0, 0.001)
    assertEquals(b(2), -0.104740810835, 0.001)
    assertEquals(b(3), 0.0, 0.001)
    assertEquals(b(4), 0.104740810835, 0.001)
    assertEquals(b(5), 0.0, 0.001)
    assertEquals(b(6), -0.0349136036115, 0.001)
  }




}
