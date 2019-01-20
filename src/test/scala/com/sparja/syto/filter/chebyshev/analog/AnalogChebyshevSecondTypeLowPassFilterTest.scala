package com.sparja.syto.filter.chebyshev.analog

import junit.framework.TestCase.assertEquals
import org.junit.Test

class AnalogChebyshevSecondTypeLowPassFilterTest {

  /*
  zeros: [ 0.-1.41421356j -0.+1.41421356j]
  poles: [-0.46500297-0.64515716j -0.46500297+0.64515716j]
  zeros: [ 0.-1.41421356j -0.+1.41421356j]
  poles: [-0.3-0.33166248j -0.3+0.33166248j]
  zeros: [ 0.-1.41421356j -0.+1.41421356j]
  poles: [-0.17499365-0.18061776j -0.17499365+0.18061776j]
   */

  @Test
  def shouldFindZerosAndPoles() = {
   val filter = AnalogChebyshevSecondTypeLowPassFilter(2, 10f, 1)
    assertEquals(filter.zeros.size, 2)
    assertEquals(filter.zeros(0).real, 0, 0.001)
    assertEquals(filter.zeros(0).imag, 1.4142, 0.001)
    assertEquals(filter.zeros(1).real, 0, 0.001)
    assertEquals(filter.zeros(1).imag, -1.4142, 0.001)

    assertEquals(filter.poles.size, 2)
    assertEquals(filter.poles(0).real, -0.465002, 0.001)
    assertEquals(filter.poles(0).imag, 0.64515, 0.001)
    assertEquals(filter.poles(1).real, -0.465002, 0.001)
    assertEquals(filter.poles(1).imag, -0.64515, 0.001)
  }

  /*
  zeros: [ 0.-1.15470054j -0.+1.15470054j]
  poles: [-0.27645192-0.88440988j -1.55290384-0.j         -0.27645192+0.88440988j]
  */

  @Test
  def shouldFindZeros() = {
    val filter = AnalogChebyshevSecondTypeLowPassFilter(3, 10f, 1)
    assertEquals(filter.zeros.size, 2)
    assertEquals(filter.zeros(0).real, 0, 0.001)
    assertEquals(filter.zeros(0).imag, 1.15470054, 0.001)
    assertEquals(filter.zeros(1).real, 0, 0.001)
    assertEquals(filter.zeros(1).imag, -1.15470054, 0.001)
  }

  /*
  zeros: [ 0.-1.05146222j  0.-1.70130162j -0.+1.70130162j -0.+1.05146222j]
  poles: [-0.11017418-0.97308653j -0.62179455-1.29644957j -2.68990741-0.j  -0.62179455+1.29644957j -0.11017418+0.97308653j]
   */

  @Test
  def shouldFindZerosFiveOrder() = {
    val filter = AnalogChebyshevSecondTypeLowPassFilter(5, 10f, 1)
    assertEquals(filter.zeros.size, 4)
    assertEquals(filter.zeros(0).imag, 1.05146222, 0.001)
    assertEquals(filter.zeros(1).imag, 1.70130162, 0.001)
    assertEquals(filter.zeros(2).imag, -1.05146222, 0.001)
    assertEquals(filter.zeros(3).imag, -1.70130162, 0.001)
  }

  @Test
  def shouldCalculateTwoOrderFilter() = {
    val (b, a) = AnalogChebyshevSecondTypeLowPassFilter(2, 10f, 1f).calculateCoefficients()
    assertEquals(a(0), 1.0, 0.001d)
    assertEquals(a(1), 0.930005948404, 0.001)
    assertEquals(a(2), 0.632455532034, 0.001)
    assertEquals(b(0), 0.316227766017, 0.001)
    assertEquals(b(1), 0.0, 0.001)
    assertEquals(b(2), 0.632455532034, 0.001)
  }

  @Test
  def shouldCalculateThreeOrderFilter() = {
    val (b, a) = AnalogChebyshevSecondTypeLowPassFilter(3, 10f, 1f).calculateCoefficients()
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
    val (b, a) = AnalogChebyshevSecondTypeLowPassFilter(4, 10f, 1f).calculateCoefficients()
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
    val (b, a) = AnalogChebyshevSecondTypeLowPassFilter(5, 10f, 1f).calculateCoefficients()
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
    val (b, a) = AnalogChebyshevSecondTypeLowPassFilter(5, 10.0, 10.0).calculateCoefficients()
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
    val (b, a) = AnalogChebyshevSecondTypeLowPassFilter(5, 10f, 15f).calculateCoefficients()
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
