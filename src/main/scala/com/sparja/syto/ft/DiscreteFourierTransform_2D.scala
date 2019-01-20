package com.sparja.syto.ft

import breeze.math.Complex
import breeze.numerics.{cos, sin}

object DiscreteFourierTransform_2D {


    def exponentialMonom(length: Int) = {
      (k: Int, n: Int) =>  {
        val angle = k * 2 * Math.PI * n / length
        Complex(cos(angle), -sin(angle))
      }
    }
    //val e = exponentialMonom(xdata.size)
   // val fourierCoefficients = (0 to xdata.size - 1).
    //  map(k => xdata.zipWithIndex.map{case(x, n) => x * e(k, n)}.sum )



  def main(args: Array[String]): Unit = {
    val arr = List(List(1,3,2,4), List(2,1,3,4))
    val rowNums = arr.size
    val colNums = arr.head.size

    val ek = exponentialMonom(rowNums)
    val el = exponentialMonom(colNums)

    val  coefficientIndicies =  for { k <- (0 to rowNums - 1); l <- (0 to colNums - 1) } yield (k,l)
    val  elementIndicies =  for { n <- (0 to rowNums - 1); m <- (0 to colNums - 1) } yield (n,m)

    val fourierCoefficients = coefficientIndicies.map{case(k, l) => elementIndicies.map{case(n, m) => arr(n)(m) * ek(k, n) * el(l,m)}.sum}
    fourierCoefficients.foreach(println)

  }

}
