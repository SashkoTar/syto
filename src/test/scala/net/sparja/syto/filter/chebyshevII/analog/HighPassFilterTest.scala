package net.sparja.syto.filter.chebyshevII.analog

import net.sparja.syto.filter.{Approximation, TransferFunctionBuilder}
import junit.framework.TestCase.assertEquals
import org.junit.Test

class HighPassFilterTest {

 def calculateCoefficients(order: Int, ripple: Double, cufOffFreq: Double) = {
   new TransferFunctionBuilder()
     .chebyshevIIApproximation(order, ripple)
     .transformToHighPass(cufOffFreq)
     .coefficients
 }

  @Test
  def shouldCalculateThreeOrderFilter() = {
    val (b, a) = calculateCoefficients(3, 10f, 100f)
    assertEquals(a(0), 1.0, 0.001)
    assertEquals(a(1), 128.79097507, 0.001)
    assertEquals(a(2), 15793.5576298, 0.001)
    assertEquals(a(3), 750000.0, 0.001)
    assertEquals(b(0), 1.0, 0.001)
    assertEquals(b(1), 0.0, 0.001)
    assertEquals(b(2), 7500.0, 0.001)
    assertEquals(b(3), 0.0, 0.001)
  }

  @Test
  def shouldCalculateTwoOrderFilter() = {
    val (b, a) = calculateCoefficients(2, 20f, 5f)
    assertEquals(a(0), 1.0, 0.001)
    assertEquals(a(1), 15.0, 0.001)
    assertEquals(a(2), 125.0, 0.001)
    assertEquals(b(0), 1.0, 0.001)
    assertEquals(b(1), 0.0, 0.001)
    assertEquals(b(2), 12.5, 0.001)
  }

  @Test
  def shouldCalculateTwoOrderFilter2() = {
    val (b, a) = calculateCoefficients(2, 20f, 7f)
    assertEquals(a(0), 1.0, 0.001)
    assertEquals(a(1), 21.0, 0.001)
    assertEquals(a(2), 245.0, 0.001)
    assertEquals(b(0), 1.0, 0.001)
    assertEquals(b(1), 0.0, 0.001)
    assertEquals(b(2), 24.5, 0.001)
  }

  @Test
  def shouldCalculateThreeOrderFilter2() = {
    val (b, a) = calculateCoefficients(4, 20f, 230f)
    assertEquals(a(0), 1.0, 0.001)
    assertEquals(a(1), 492.910283576, 0.001)
    assertEquals(a(2), 174380.273827, 0.1)
    assertEquals(a(3), 34394182.3998, 0.1)
    assertEquals(a(4), 3498012500.0, 0.1)
    assertEquals(b(0), 1.0, 0.001)
    assertEquals(b(1), 0.0, 0.001)
    assertEquals(b(2), 52900.0, 0.001)
    assertEquals(b(3), 0.0, 0.001)
    assertEquals(b(4), 349801250.0, 0.001)
  }
}
