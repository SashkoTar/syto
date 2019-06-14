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

  [0.00041655 0.00124964 0.00124964 0.00041655]
  [ 1.         -2.6861574   2.41965511 -0.73016535]
  [ 0.99958345 -1.68782358  0.73058189]


  b, a = signal.butter(3, 0.05)
  zi = signal.lfilter_zi(b, a)

   */

  @Test
  def shouldCalculateSteadyStateResponse():Unit = {
    val b = List(0.00041655, 0.00124964, 0.00124964, 0.00041655)
    val a = List(1.0, -2.6861574, 2.41965511, -0.73016535)
    val zi = lfilter_zi(a, b)
    assertEquals(zi(0), 0.99958345, 0.00001)
    assertEquals(zi(1), -1.68782358, 0.00001)
    assertEquals(zi(2), 0.73058189, 0.00001)
  }



  /*
  a = np.array([[2,1,1], [4,-6,0], [-2,7,2]])
  b = np.array([5,-2,9])
   */
  @Test
  def shouldSolveEquation() = {
  val a =  DenseMatrix((2.0,1.0,1.0), (4.0,-6.0,0.0), (-2.0,7.0,2.0))
  val b =  DenseVector(5.0,-2.0,9.0)
    println(a\b)

  }

}
