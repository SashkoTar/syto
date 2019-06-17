package com.sparja.syto.filter

object FilterImplementation {


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
      //println(s"output($n)=${output(n)}")
    })

    output
  }

  def lfilter_zi(a: List[Double], b: List[Double]) = a

}
