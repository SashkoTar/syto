package com.sparja.syto.filter

import com.sparja.syto.filter.FiltfiltImplementation.{filtfilt, generatePad}
import junit.framework.TestCase.assertEquals
import org.junit.Test

class FiltfiltTest {


  @Test
  def shouldFiltfiltData20() = {
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
  def shouldFiltfiltData40FourOrder() = {
    val a = List(1, -1.57039885, 1.27561332, -0.48440337, 0.07619706)
    val b = List(0.01856301, 0.07425204, 0.11137806, 0.07425204, 0.01856301)
    val x = (1 to 40).map(_.toDouble).toList
    val y = filtfilt(b, a, x)

    assertEquals(y(0), 0.99885024, 0.00001)
    assertEquals(y(1), 1.99853888, 0.00001)
    assertEquals(y(2), 2.99929456, 0.00001)
    assertEquals(y(39), 39.99206, 0.00001)
  }



  @Test(expected = classOf[IllegalArgumentException])
  def shouldThrowException() = {
    val a = List(1, -1.16191748, 0.69594276, -0.1377613)
    val b = List(0.049533, 0.14859899, 0.14859899, 0.049533)
    val x = (1 to 10).map(_.toDouble).toList
    val y = filtfilt(b, a, x)
  }

  @Test(expected = classOf[IllegalArgumentException])
  def shouldThrowExceptionWhenInputIsEmpty() = {
    val a = List(1, -1.16191748, 0.69594276, -0.1377613)
    val b = List(0.049533, 0.14859899, 0.14859899, 0.049533)
    val x = List()
    val y = filtfilt(b, a, x)
  }

  @Test
  def shouldGeneratePadForEmptyInput(): Unit = {
    val a = List(1, -1.16191748, 0.69594276, -0.1377613)
    val b = List(0.049533, 0.14859899, 0.14859899, 0.049533)
    val x = List()
    val (edge, ext) = generatePad(b, a, x)
    assertEquals(edge, 12)
  }


}
