package com.sparja.syto.filter

import org.junit.Test


class FilterImplementationTest {
  /*


  [0.049533   0.14859899 0.14859899 0.049533  ]
[ 1.         -1.16191748  0.69594276 -0.1377613 ]
[ 0.049533    0.30521824  0.91456223  1.84771508  2.93938066  4.038593
  5.08086662  6.07356693  7.04933289  8.03207878  9.02789081 10.0316941
 11.03665084 12.03918636 13.03920676 14.03814873 15.03725449 16.0369546
 17.03708273 18.03731712]


   */


  def filter(a: List[Double], b: List[Double], inputData: List[Int]) = {
    val output = new Array[Double](inputData.size)

    def y(i: Int) = if (i >=0 && i <= output.size - 1) output(i) else 0.0

    def x(i: Int) = if (i >=0 && i <= inputData.size - 1) inputData(i) else 0

   inputData.indices.foreach(n => {
     val xn = x(n)
     val xn_1 = x(n-1)
     val xn_2 = x(n-2)
     val xn_3 = x(n-3)
     val yn_1 = y(n-1)
     val yn_2 = y(n-2)
     val yn_3 = y(n-3)
     output(n) = b(0)*xn + b(1)*xn_1 + b(2)*xn_2 + b(3)*xn_3 - a(1)*yn_1 - a(2)*yn_2 - a(3)*yn_3
     println(s"output($n)=${output(n)}")
   })

    output
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

}
