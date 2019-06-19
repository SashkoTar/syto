package com.sparja.syto.filter

import breeze.linalg.{DenseMatrix, DenseVector}
import org.junit.Test
import com.sparja.syto.filter.FilterImplementation.{filter, lfilter_zi}
import junit.framework.TestCase.assertEquals

class FilterImplementationTest {

  @Test
  def shouldFilterOneSignalThreeOrder() = {
    val a = List(1, -1.16191748, 0.69594276, -0.1377613)
    val b = List(0.049533, 0.14859899, 0.14859899, 0.049533)
    val x = List(1)
    val y = filter(a, b, x)

    assertEquals(y(0), 0.049533, 0.00001)
    assertEquals(y.size, 1)
  }



  @Test
  def shouldFilterTenSignalsThreeOrder() = {
    val a = List(1, -1.16191748, 0.69594276, -0.1377613)
    val b = List(0.049533, 0.14859899, 0.14859899, 0.049533)
    val x = (1 to 10).toList
    val y = filter(a, b, x)

    assertEquals(y(0), 0.049533, 0.00001)
    assertEquals(y(1), 0.30521824, 0.00001)
    assertEquals(y(2), 0.91456223, 0.00001)
    assertEquals(y(9), 8.03207878, 0.00001)
    assertEquals(y.size, 10)
  }

  @Test
  def shouldFilterOneSignalFourOrder() = {
    val a = List(1, -1.57039885,  1.27561332, -0.48440337,  0.07619706)
    val b = List(0.01856301, 0.07425204, 0.11137806, 0.07425204, 0.01856301)
    val x = List(1)
    val y = filter(a, b, x)

    assertEquals(y(0), 0.01856301, 0.00001)
    assertEquals(y.size, 1)
  }

  /*
  [1.85630106e-02 1.40529394e-01 5.12579156e-01 1.22870088e+00
 2.23338062e+00 3.36557097e+00 4.47752937e+00 5.50862253e+00
 6.47831241e+00 7.43521111e+00 8.41271960e+00 9.41532801e+00
 1.04295459e+01 1.14409355e+01 1.24436625e+01 1.34401047e+01
 1.44354728e+01 1.54331902e+01 1.64335831e+01 1.74351391e+01
 1.84363287e+01 1.94365763e+01 2.04361714e+01 2.14356774e+01
 2.24354475e+01 2.34355014e+01 2.44356711e+01 2.54357950e+01
 2.64358167e+01 2.74357709e+01]
   */

  @Test
  def shouldFilterTenSignalsFourOrder() = {
    val a = List(1, -1.57039885,  1.27561332, -0.48440337,  0.07619706)
    val b = List(0.01856301, 0.07425204, 0.11137806, 0.07425204, 0.01856301)
    val x = (1 to 30).toList
    val y = filter(a, b, x)

    assertEquals(y(0), 0.01856301, 0.00001)
    assertEquals(y(10), 8.4127196, 0.00001)
    assertEquals(y(20), 1.84363287e+01, 0.00001)
    assertEquals(y(29), 2.74357709e+01, 0.00001)
    assertEquals(y.size, 30)
  }


  @Test
  def shouldFilterData() = {
   println(filter(List(1, -1.16191748,  0.69594276, -0.1377613),
     List(0.049533, 0.14859899, 0.14859899, 0.049533),
     (1 to 30).toList
   ))

  }


  @Test
  def shouldFilterDataFor1() = {
    println(filter(List(1, -1.16191748,  0.69594276, -0.1377613),
      List(0.049533, 0.14859899, 0.14859899, 0.049533),
      (1 to 30).toList
    ))
  }


  /*

  b = [0.00041655 0.00124964 0.00124964 0.00041655]
  a = [ 1.         -2.6861574   2.41965511 -0.73016535]
  zi = [ 0.99958345 -1.68782358  0.73058189]


  b, a = signal.butter(3, 0.05)
  zi = signal.lfilter_zi(b, a)

   */

  @Test
  def shouldCalculateSteadyStateResponse():Unit = {
    val b = List(0.00041655, 0.00124964, 0.00124964, 0.00041655)
    val a = List(1.0, -2.6861574, 2.41965511, -0.73016535)
    val zi = lfilter_zi(a, b)
    assertEquals(zi(0), 0.99958345, 0.0001)
    assertEquals(zi(1), -1.68782358, 0.0001)
    assertEquals(zi(2), 0.73058189, 0.0001)
  }

  /*
   b, a = signal.butter(4, 0.05)
   zi = signal.lfilter_zi(b, a)

    b = [3.12389769e-05 1.24955908e-04 1.87433862e-04 1.24955908e-04  3.12389769e-05]
    a = [ 1.         -3.58973389  4.85127588 -2.92405266  0.66301048]
    zi = [ 0.99996876 -2.58989008  2.26119837 -0.66297925]
  */

  @Test
  def shouldCalculateSteadyStateResponse2():Unit = {
    val b = List(3.12389769e-05, 1.24955908e-04, 1.87433862e-04, 1.24955908e-04,  3.12389769e-05)
    val a = List(1.0, -3.58973389,  4.85127588, -2.92405266,  0.66301048)
    val zi = lfilter_zi(a, b)
    assertEquals(zi(0), 0.99996876, 0.0001)
    assertEquals(zi(1), -2.58989008, 0.0001)
    assertEquals(zi(2), 2.26119837, 0.0001)
    assertEquals(zi(3), -0.66297925, 0.0001)
  }


}
