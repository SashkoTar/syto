package com.sparja.syto.filter.chebyshev.analog

import breeze.math.Complex
import breeze.numerics.{cos, pow, sin, sqrt}
import com.sparja.syto.filter.core.TransferFunction
import com.sparja.syto.polynomial.PolynomialSupport
import org.apache.commons.math3.util.FastMath

object AnalogChebyshevFirstTypeBandStopFilter {

  def apply(order: Int, rp: Float, lowFreq: Double, highFreq: Double): AnalogFirstTypeBandStopTransferFunction = {
    new AnalogFirstTypeBandStopTransferFunction(order, rp, lowFreq, highFreq)
  }


  class AnalogFirstTypeBandStopTransferFunction(order: Int, rp: Float, lowFreq: Double, highFreq: Double) extends TransferFunction {

    val eps = Math.sqrt(Math.pow(10, 0.1 * rp) - 1.0)
    val mu =  FastMath.asinh(1 / eps) / order

    val zeros = List.empty[Complex]

     val poles = {
       (1 to order)
         .map(k => (2 * k - 1) * Math.PI / (2 * order))
         .map(theta => Complex(-sin(theta) * Math.sinh(mu), cos(theta) * Math.cosh(mu))).toList
    }

    val scale = Math.pow(-1, order) * (if (order%2 == 0) poles.product/sqrt(1 + eps*eps) else poles.product)

    val bandWidth = highFreq - lowFreq

    val wo = Math.sqrt(lowFreq * highFreq)

    def transformPoint(point: Complex):Complex = {
      (-bandWidth / point + (bandWidth*bandWidth / (point*point) - 4*wo*wo).pow(0.5))/2
    }

    def transformPoint2(point: Complex):Complex = {
      (-bandWidth / point - (bandWidth * bandWidth / (point * point) - 4 * wo * wo).pow(0.5)) / 2
    }

    def calculateCoefficients(): (List[Double], List[Double]) = {

      val degree = poles.size - zeros.size

      val transformedPoles = (poles.map(transformPoint) ::: poles.map(transformPoint2)).map(_.unary_-)
      val transformedZeros = (zeros.map(transformPoint) ::: zeros.map(transformPoint2)).map(_.unary_-) ::: List.fill(degree)(wo*Complex.i) ::: List.fill(degree)(wo*Complex.i.unary_-)

      val b = PolynomialSupport.calculateCoefficients(transformedZeros).map(_.real)
      val a = PolynomialSupport.calculateCoefficients(transformedPoles).map(_.real)

      val k = Math.pow(-1, order) * scale * zeros.product/poles.product

      (b.map(_ * k.real), a)

    }

  }

}