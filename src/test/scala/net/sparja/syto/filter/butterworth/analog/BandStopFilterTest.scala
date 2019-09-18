package net.sparja.syto.filter.butterworth.analog

import net.sparja.syto.filter.{Approximation, TransferFunctionBuilder}
import junit.framework.TestCase.assertEquals
import org.junit.Test

class BandStopFilterTest {

  def calculateCoefficients(order: Int, lowFreq: Double, highFreq: Double) = {
    new TransferFunctionBuilder()
      .butterworthApproximation(order)
      .transformToBandStop(lowFreq, highFreq)
      .coefficients
  }

  @Test
  def shouldCalculateTwoOrderFilter() = {

    val (b, a) = calculateCoefficients(2, 10.0, 20.0)
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

    val (b, a) = calculateCoefficients(3, 10.0, 20.0)
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
