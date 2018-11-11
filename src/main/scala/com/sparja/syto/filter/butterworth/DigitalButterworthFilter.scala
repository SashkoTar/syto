package com.sparja.syto.filter.butterworth

import breeze.math.Complex
import breeze.numerics.{cos, sin}
import com.sparja.syto.polynomial.PolynomialSupport

abstract class DigitalButterworthFilter(order: Int, sampleFrequency: Float) {

  val zeros = List.fill(order)(Complex.one.unary_-)

  // poles of filter with cutoff = 1 rad/s
  val normalizedPoles = (1 to order)
    .map(k => (2 * k - 1) * Math.PI / (2 * order))
    .map(theta => Complex(-sin(theta), cos(theta))).toList

  def calculateCoefficients(): (List[Float], List[Float]) = {

    val scaledPa = preWarpAndTransformToSpecificFilterType(normalizedPoles)

    val poles = transformBilinear(scaledPa)

    val (a, b) = findCoefficients(poles, zeros)

    val k = scaleFactor(a, b)

    (b.map(_ * k).map(_.toFloat), a.map(_.toFloat))
  }

  def preWarpAndTransformToSpecificFilterType(pa: List[Complex]): List[Complex]

  //poles by bilinear transform
  def transformBilinear(poles: List[Complex]) = {
    poles.map(p => {
      val num = 1 + p/(2 * sampleFrequency)
      val denum = 1 - p/(2 * sampleFrequency)
      num / denum
    })
  }

  def findCoefficients(poles: List[Complex], zeros: List[Complex]) = {
    val a = PolynomialSupport.calculateCoefficients(poles).map(_.real)
    val b = PolynomialSupport.calculateCoefficients(zeros).map(_.real)
    (a, b)
  }

  def scaleFactor(a: List[Double], b:List[Double]) = {
    a.sum/b.sum
  }


}
