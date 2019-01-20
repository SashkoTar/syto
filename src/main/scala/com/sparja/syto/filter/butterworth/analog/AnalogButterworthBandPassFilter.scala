package com.sparja.syto.filter.butterworth.analog

import breeze.math.Complex
import breeze.numerics.{cos, pow, sin}
import com.sparja.syto.filter.core.TransferFunction
import com.sparja.syto.polynomial.PolynomialSupport

object AnalogButterworthBandPassFilter {

  def apply(order: Int, lowFreq: Double, highFreq: Double) = new BandPassTransferFunction(order, lowFreq, highFreq)

  class BandPassTransferFunction(order: Int, lowFreq: Double, highFreq: Double)  extends TransferFunction {

    val poles = (1 to order)
      .map(k => (2 * k - 1) * Math.PI / (2 * order))
      .map(theta => Complex(-sin(theta), cos(theta))).toList

    val zeros = List.fill(order)(Complex.zero)

    val bandWidth = highFreq - lowFreq

    val wo = Math.sqrt(lowFreq * highFreq)

    def transformPoint(point: Complex):Complex = {
      (-bandWidth * point + (bandWidth*bandWidth*point*point - 4*wo*wo).pow(0.5))/2
    }

    def transformPoint2(point: Complex):Complex = {
      (-bandWidth * point - (bandWidth*bandWidth*point*point - 4*wo*wo).pow(0.5))/2
    }

    /*
    zeros: [0.+0.j 0.+0.j]
    poles: [-2.65336507-10.6340893j  -2.65336507+10.6340893j  -4.41770274+17.70515711j -4.41770274-17.70515711j]

    zeros: []
    poles: [-0.70710678+0.70710678j -0.70710678-0.70710678j]
     */
    override def calculateCoefficients(): (List[Double], List[Double]) = {

      val transformedPoles = (poles.map(transformPoint) ::: poles.map(transformPoint2)).map(_.unary_-)
      val transformedZeros = zeros

      val b = PolynomialSupport.calculateCoefficients(zeros).map(_.real)
      val a = PolynomialSupport.calculateCoefficients(transformedPoles).map(_.real)

      val k = pow(bandWidth, transformedPoles.size - zeros.size)
      (b.map(_ * k), a)
    }
  }

}
