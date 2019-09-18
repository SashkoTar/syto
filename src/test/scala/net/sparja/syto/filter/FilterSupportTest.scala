package net.sparja.syto.filter

import junit.framework.TestCase.assertEquals
import org.junit.Test

class FilterSupportTest {

  @Test
  def shouldCalculateFrequencyResponse() = {
    val a = List(1, -1.13608549391, 1.97230236061, -0.9497603088, 0.700896781188)
    val b = List(1.0, 0, -2, 0, 1)
    val f0 = Math.sqrt(18*22)
    val fs = 100

    val magnitude = frequencyResponse(a, b, f0, fs)
    assertEquals(1/magnitude, 0.0133592, 0.001)
  }

}
