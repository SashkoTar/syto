package com.sparja.syto.breeze

object SignalCalculation {


  def main(args: Array[String]): Unit = {
    import breeze.linalg.DenseVector
    import breeze.signal._

    val a = DenseVector(1, -0.4, -0.05)
    val b = DenseVector(1, -0.1, -0.06, 0.7)

    val v = convolve(a, b)
    println(v.data)
  }
}
