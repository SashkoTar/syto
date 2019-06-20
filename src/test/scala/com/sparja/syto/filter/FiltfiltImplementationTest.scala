package com.sparja.syto.filter

import com.sparja.syto.filter.FilterImplementation.lfilter_zi
import com.sparja.syto.filter.FiltfiltImplementation.{filtfilt, computeZi}
import junit.framework.TestCase.assertEquals
import org.junit.Test

class FiltfiltImplementationTest {

  @Test
  def shouldFiltfiltData10() = {
    val a = List(1, -1.16191748, 0.69594276, -0.1377613)
    val b = List(0.049533, 0.14859899, 0.14859899, 0.049533)
    val x = (1 to 20).map(_.toDouble).toList
    val y = filtfilt(b, a, x)

    assertEquals(y(0), 1.00095184, 0.00001)
    assertEquals(y(1), 2.00121629, 0.00001)
    assertEquals(y(2), 3.00061229, 0.00001)
    assertEquals(y(9), 9.99994574, 0.00001)
    assertEquals(y(19), 20.00569485, 0.00001)
  }


  @Test
  def shouldCalculateSteadyStateResponse():Unit = {
    val b = List(0.00041655, 0.00124964, 0.00124964, 0.00041655)
    val a = List(1.0, -2.6861574, 2.41965511, -0.73016535)
    val zi = lfilter_zi(a, b)
    //val zi = computeZi(b.toIndexedSeq, a.toIndexedSeq)
    assertEquals(zi(0), 0.99958345, 0.0001)
    assertEquals(zi(1), -1.68782358, 0.0001)
    assertEquals(zi(2), 0.73058189, 0.0001)
  }

}
