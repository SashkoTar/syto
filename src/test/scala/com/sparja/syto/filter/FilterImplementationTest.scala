package com.sparja.syto.filter

import breeze.linalg.{DenseMatrix, DenseVector}
import org.junit.Test
import com.sparja.syto.filter.FilterImplementation.{filter, lfilter_zi}
import junit.framework.TestCase.assertEquals

class FilterImplementationTest {
  /*

  [0.049533   0.14859899 0.14859899 0.049533  ]
[ 1.         -1.16191748  0.69594276 -0.1377613 ]
[ 0.049533    0.30521824  0.91456223  1.84771508  2.93938066  4.038593
  5.08086662  6.07356693  7.04933289  8.03207878  9.02789081 10.0316941
 11.03665084 12.03918636 13.03920676 14.03814873 15.03725449 16.0369546
 17.03708273 18.03731712]

   */

  /*
  [0.049533   0.14859899 0.14859899 0.049533  ]
[ 1.         -1.16191748  0.69594276 -0.1377613 ]
LFILT:
[0.049533   0.30521824 0.91456223 1.84771508 2.93938066 4.038593
 5.08086662 6.07356693 7.04933289 8.03207878]
   */

  @Test
  def shouldFilterData10() = {
    /*
    println(filter(List(1, -1.16191748, 0.69594276, -0.1377613),
      List(0.049533, 0.14859899, 0.14859899, 0.049533),
      (1 to 10).toList
    ))*/
    val a = List(1, -1.16191748, 0.69594276, -0.1377613)
    val b = List(0.049533, 0.14859899, 0.14859899, 0.049533)
    val x = (1 to 10).toList
    val y = filter(a, b, x)

    assertEquals(y(0), 0.049533, 0.00001)
    assertEquals(y(1), 0.30521824, 0.00001)
    assertEquals(y(2), 0.91456223, 0.00001)
    assertEquals(y(9), 8.03207878, 0.00001)

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
