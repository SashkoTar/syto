package com.sparja.syto.filter.chebyshevII.analog


import com.sparja.syto.filter.core.{Prototype, TransferFunctionBuilder}
import junit.framework.TestCase.assertEquals
import org.junit.Test

class LowPassFilterTest {


  def calculateCoefficients(order: Int, ripple: Double, cufOffFreq: Double) = {
    new TransferFunctionBuilder()
      .prototype(Prototype.chebyshevII, order, ripple)
      .transformToLowPass(cufOffFreq)
      .coefficients
  }


  @Test
  def shouldCalculateTwoOrderFilter() = {
    val (b, a) =  calculateCoefficients(2, 10, 1)

    assertEquals(a(0), 1.0, 0.001d)
    assertEquals(a(1), 0.930005948404, 0.001)
    assertEquals(a(2), 0.632455532034, 0.001)
    assertEquals(b(0), 0.316227766017, 0.001)
    assertEquals(b(1), 0.0, 0.001)
    assertEquals(b(2), 0.632455532034, 0.001)
  }

  @Test
  def shouldCalculateThreeOrderFilter() = {
    val (b, a) =  calculateCoefficients(3, 10, 1)

    assertEquals(a(0), 1.0, 0.001)
    assertEquals(a(1), 2.10580768397, 0.001)
    assertEquals(a(2), 1.71721300094, 0.001)
    assertEquals(a(3), 1.33333333333, 0.001)
    assertEquals(b(0), 1.0, 0.001)
    assertEquals(b(1), 0.0, 0.001)
    assertEquals(b(2), 1.33333333333, 0.001)
  }

  @Test
  def shouldCalculateFourOrderFilter() = {
    val (b, a) =  calculateCoefficients(4, 10, 1)

    assertEquals(a(0), 1.0, 0.001)
    assertEquals(a(1), 2.69864199809, 0.001)
    assertEquals(a(2), 4.44133431694, 0.001)
    assertEquals(a(3), 3.10991505782, 0.001)
    assertEquals(a(4), 2.52982212813, 0.001)
    assertEquals(b(0), 0.316227766017, 0.001)
    assertEquals(b(1), 0.0, 0.001)
    assertEquals(b(2), 2.52982212813, 0.001)
    assertEquals(b(3), 0.0, 0.001)
    assertEquals(b(4), 2.52982212813, 0.001)
  }

  @Test
  def shouldCalculateNormalizedFiveOrderFilter() = {
    val (b, a) =  calculateCoefficients(5, 10, 1)

    assertEquals(a(0), 1.0, 0.001)
    assertEquals(a(1), 4.15384488072, 0.001)
    assertEquals(a(2), 7.23832475765, 0.001)
    assertEquals(a(3), 10.526151516, 0.001)
    assertEquals(a(4), 6.4162168287, 0.001)
    assertEquals(a(5), 5.33333333333, 0.001)
    assertEquals(b(0), 1.66666666667, 0.001)
    assertEquals(b(1), 0.0, 0.001)
    assertEquals(b(2), 6.66666666667, 0.001)
    assertEquals(b(3), 0.0, 0.001)
    assertEquals(b(4), 5.33333333333, 0.001)
  }


  @Test
  def shouldCalculateFiveOrderFilter() = {
    val (b, a) =  calculateCoefficients(5, 10, 10)

    assertEquals(a(0), 1.0, 0.001)
    assertEquals(a(1), 41.5384488072, 0.001)
    assertEquals(a(2), 723.832475765, 0.001)
    assertEquals(a(3), 10526.151516, 0.001)
    assertEquals(a(4), 64162.168287, 0.001)
    assertEquals(a(5), 533333.333333, 0.001)
    assertEquals(b(0), 16.6666666667, 0.001)
    assertEquals(b(1), 0.0, 0.001)
    assertEquals(b(2), 6666.66666667, 0.001)
    assertEquals(b(3), 0.0, 0.001)
    assertEquals(b(4), 533333.333333, 0.001)
  }

  @Test
  def shouldCalculateFiveOrderFilter2() = {
    val (b, a) =  calculateCoefficients(5, 10, 15)

    assertEquals(a(0), 1.0, 0.001)
    assertEquals(a(1), 62.3076732108, 0.001)
    assertEquals(a(2), 1628.62307047, 0.001)
    assertEquals(a(3), 35525.7613665, 0.001)
    assertEquals(a(4), 324820.976953, 0.001)
    assertEquals(a(5), 4050000.0, 0.001)
    assertEquals(b(0), 25.0, 0.001)
    assertEquals(b(1), 0.0, 0.001)
    assertEquals(b(2), 22500.0, 0.001)
    assertEquals(b(3), 0.0, 0.001)
    assertEquals(b(4), 4050000.0, 0.001)
  }


}
