package com.sparja.syto.filter.bessel.digital

import com.sparja.syto.filter.core.{Prototype, Roots, TransferFunctionBuilder}
import junit.framework.TestCase.assertEquals
import org.junit.Test

class BandPassFilterTest {

  def calculateCoefficients(f: () => Roots, lowFreq: Double, highFreq: Double, sampleFreq: Double) = {
    new TransferFunctionBuilder()
      .prototype(f)
      .digitalize(sampleFreq)
      .transformToBandPass(lowFreq, highFreq)
      .coefficients
  }

  @Test
  def shouldCalculateTwoOrderFilter() = {
    val (b, a) = calculateCoefficients(() => Prototype.bessel(2), 10.0, 20.0, 50.0)
    assertEquals(a(0), 1.0, 0.001)
    assertEquals(a(1), 0.893380868514, 0.001)
    assertEquals(a(2), 0.548354111324, 0.001)
    assertEquals(a(3), 0.203327354133, 0.001)
    assertEquals(a(4), 0.0967082226471, 0.001)
    assertEquals(b(0), 0.189451685995, 0.001)
    assertEquals(b(1), 0.0, 0.001)
    assertEquals(b(2), -0.378903371989, 0.001)
    assertEquals(b(3), 0.0, 0.001)
    assertEquals(b(4), 0.189451685995, 0.001)
  }

  @Test
  def shouldCalculateThreeOrderFilter() = {
    val (b, a) = calculateCoefficients(() => Prototype.bessel(3), 10.0, 20.0, 50.0)
    assertEquals(a(0), 1.0, 0.001)
    assertEquals(a(1), 1.34449421037, 0.001)
    assertEquals(a(2), 1.14476232516, 0.001)
    assertEquals(a(3), 0.682787412763, 0.001)
    assertEquals(a(4), 0.404307787274, 0.001)
    assertEquals(a(5), 0.131522864474, 0.001)
    assertEquals(a(6), 0.0338468333962, 0.001)
    assertEquals(b(0), 0.0861265569337, 0.001)
    assertEquals(b(1), 0.0, 0.001)
    assertEquals(b(2), -0.258379670801, 0.001)
    assertEquals(b(3), 0.0, 0.001)
    assertEquals(b(4), 0.258379670801, 0.001)
    assertEquals(b(5), 0.0, 0.001)
    assertEquals(b(6), -0.0861265569337, 0.001)
  }


  @Test
  def shouldCalculateThreeOrderFilterNormDelay() = {
    val (b, a) = calculateCoefficients(() => Prototype.bessel(3, "delay"), 10.0, 20.0, 50.0)
    assertEquals(a(0), 1.0, 0.001)
    assertEquals(a(1), 0.788279959709, 0.001)
    assertEquals(a(2), -0.710569751956, 0.001)
    assertEquals(a(3), -0.371422425991, 0.001)
    assertEquals(a(4), 0.377706649094, 0.001)
    assertEquals(a(5), 0.0882940027048, 0.001)
    assertEquals(a(6), -0.0627447573543, 0.001)
    assertEquals(b(0), 0.302299000291, 0.001)
    assertEquals(b(1), 0.0, 0.001)
    assertEquals(b(2), -0.906897000874, 0.001)
    assertEquals(b(3), 0.0, 0.001)
    assertEquals(b(4), 0.906897000874, 0.001)
    assertEquals(b(5), 0.0, 0.001)
    assertEquals(b(6), -0.302299000291, 0.001)
  }

  @Test
  def shouldCalculateThreeOrderFilterNormMag() = {
    val (b, a) = calculateCoefficients(() => Prototype.bessel(3, "mag"), 10.0, 20.0, 50.0)
    assertEquals(a(0), 1.0, 0.001)
    assertEquals(a(1), 1.13113959756, 0.001)
    assertEquals(a(2), 0.411104453578, 0.001)
    assertEquals(a(3), 0.155265288796, 0.001)
    assertEquals(a(4), 0.202268340815, 0.001)
    assertEquals(a(5), 0.0647315952139, 0.001)
    assertEquals(a(6), 0.00320403524116, 0.001)
    assertEquals(b(0), 0.149411432877, 0.001)
    assertEquals(b(1), 0.0, 0.001)
    assertEquals(b(2), -0.448234298632, 0.001)
    assertEquals(b(3), 0.0, 0.001)
    assertEquals(b(4), 0.448234298632, 0.001)
    assertEquals(b(5), 0.0, 0.001)
    assertEquals(b(6), -0.149411432877, 0.001)
  }


}
