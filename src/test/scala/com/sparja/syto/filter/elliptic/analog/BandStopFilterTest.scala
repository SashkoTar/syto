package com.sparja.syto.filter.elliptic.analog

import com.sparja.syto.filter.{Approximation, Roots, TransferFunctionBuilder}
import junit.framework.TestCase.assertEquals
import org.junit.Test

class BandStopFilterTest {


  def calculateCoefficients(order: Int, ripplePass: Double, rippleStop: Double, lowFreq: Double, highFreq: Double) = {
    new TransferFunctionBuilder()
      .ellipticApproximation(order, ripplePass, rippleStop)
      .transformToBandStop(lowFreq, highFreq)
      .coefficients
  }

  @Test
  def shouldCalculateTwoOrderFilter() = {
    val (b, a) = calculateCoefficients(2, 5, 40, 10.0, 20.0)
    assertEquals(a(0), 1.0, 0.001)
    assertEquals(a(1), 7.38985470674, 0.001)
    assertEquals(a(2), 562.98980615, 0.01)
    assertEquals(a(3), 1477.97094135, 0.1)
    assertEquals(a(4), 40000.0, 0.001)
    assertEquals(b(0), 0.56234132519, 0.001)
    assertEquals(b(1), 0.0, 0.001)
    assertEquals(b(2), 226.566448507, 0.01)
    assertEquals(b(3), 0.0, 0.001)
    assertEquals(b(4), 22493.6530076, 0.1)
  }

  @Test
  def shouldCalculateThreeOrderFilter() = {
    val (b, a) = calculateCoefficients(3, 5, 40, 10.0, 20.0)
    assertEquals(a(0), 1.0, 0.001)
    assertEquals(a(1), 44.710406363, 0.001)
    assertEquals(a(2), 816.536892549, 0.01)
    assertEquals(a(3), 23008.4543906, 0.5)
    assertEquals(a(4), 163307.37851, 1.1)
    assertEquals(a(5), 1788416.25452, 200.1) //TODO investigate precision
    assertEquals(a(6), 8000000.0, 0.001)
    assertEquals(b(0), 1.0, 0.001)
    assertEquals(b(1), 0.0, 0.001)
    assertEquals(b(2), 624.872981431, 0.01)
    assertEquals(b(3), 0.0, 0.001)
    assertEquals(b(4), 124974.596286, 0.5)
    assertEquals(b(5), 0.0, 0.001)
    assertEquals(b(6), 8000000.0, 0.001)
  }



}
