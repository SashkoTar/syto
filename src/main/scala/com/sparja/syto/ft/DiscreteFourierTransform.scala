package com.sparja.syto.ft

import breeze.math.Complex
import breeze.numerics.{cos, sin}

object DiscreteFourierTransform {


  def discreteFourierTransform(xdata: List[Complex]) = {
    def exponentialMonom(length: Int) = {
      (k: Int, n: Int) =>  {
        val angle = k * 2 * Math.PI * n / length
        Complex(cos(angle), -sin(angle))
      }
    }
    val e = exponentialMonom(xdata.size)
    val fourierCoefficients = (0 to xdata.size - 1).
      map(k => xdata.zipWithIndex.map{case(x, n) => x * e(k, n)}.sum )

    fourierCoefficients
  }


  def x(n: Int): Double = 2 - Math.cos(2*Math.PI*n/4 - Math.PI/6) + Math.cos(4*Math.PI*n/4)
  val xSequence = (0 to 3).map(x).map(Complex(_, 0)).toList
  val xSeq2_3_5 = List(Complex(1,3), Complex(2,2), Complex(3,1), Complex(4, -4))

  def main(args: Array[String]): Unit = {
    val xdata = xSequence
    discreteFourierTransform(xdata).foreach(println)
  }

}
