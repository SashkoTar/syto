package com.sparja.syto.filter

import breeze.math.Complex
import breeze.numerics.{cos, sin}

object FilterSupport {

  def frequencyResponse(a: List[Double], b: List[Double], f0: Double, fs: Float): Double = {
    val angle = 2 * Math.PI * f0/fs
    val z = Complex(cos(angle), sin(angle))
    val numerator = b.zipWithIndex.map(e => e._1 * z.pow(-e._2)).sum
    val denumerator = a.zipWithIndex.map(e => e._1 * z.pow(-e._2)).sum
    (numerator/denumerator).abs
  }

}
