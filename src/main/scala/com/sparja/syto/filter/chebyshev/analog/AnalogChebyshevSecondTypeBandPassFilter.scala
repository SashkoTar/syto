package com.sparja.syto.filter.chebyshev.analog

import breeze.math.Complex
import breeze.numerics.{cos, pow, sin}
import com.sparja.syto.filter.core.TransferFunction
import com.sparja.syto.polynomial.PolynomialSupport
import org.apache.commons.math3.util.FastMath

object AnalogChebyshevSecondTypeBandPassFilter {

  def apply(order: Int, rp: Float, lowFreq: Double, highFreq: Double): AnalogSecondTypeBandPassTransferFunction = {
    new AnalogSecondTypeBandPassTransferFunction(order, rp, lowFreq, highFreq)
  }


  class AnalogSecondTypeBandPassTransferFunction(order: Int, rp: Float, lowFreq: Double, highFreq: Double) extends TransferFunction {

    val zeros = {
      val n = if (order%2 == 0) order / 2 else (order - 1) / 2
      val first =  (1 to n).map(k => (2 * k - 1) * Math.PI / (2 * order))
        .map(theta => Complex.i/cos(theta)).toList

      first:::first.map(_.conjugate)

    }

     val poles = {
      val eps = Math.sqrt(Math.pow(10, 0.1 * rp) - 1.0)
      val mu =  FastMath.asinh(eps) / order

      def r(theta: Double) = -sin(theta) * Math.sinh(mu) / (cos(theta) * Math.cosh(mu) * cos(theta) * Math.cosh(mu) + sin(theta) * Math.sinh(mu) * sin(theta) * Math.sinh(mu))

      def im(theta: Double) = cos(theta) * Math.cosh(mu) / (cos(theta) * Math.cosh(mu) * cos(theta) * Math.cosh(mu) + sin(theta) * Math.sinh(mu) * sin(theta) * Math.sinh(mu))

      (1 to order)
        .map(k => (2 * k - 1) * Math.PI / (2 * order))
        .map(theta => Complex(r(theta), im(theta))).toList

    }

    def calculateCoefficients(): (List[Double], List[Double]) = {
      val bandWidth = highFreq - lowFreq

      val wo = Math.sqrt(lowFreq * highFreq)

      def transformPoint(point: Complex):Complex = {
        (-bandWidth * point + (bandWidth*bandWidth*point*point - 4*wo*wo).pow(0.5))/2
      }

      def transformPoint2(point: Complex):Complex = {
        (-bandWidth * point - (bandWidth*bandWidth*point*point - 4*wo*wo).pow(0.5))/2
      }

      val transformedPoles = (poles.map(transformPoint) ::: poles.map(transformPoint2)).map(_.unary_-)
      val transformedZeros = (zeros.map(transformPoint) ::: zeros.map(transformPoint2)).map(_.unary_-):::List.fill(poles.size - zeros.size)(Complex.zero)

      /*
      zeros: [ 0.-1.41421356j -0.+1.41421356j]
      poles: [-0.46500297-0.64515716j -0.46500297+0.64515716j]

      zeros: [0.-22.88245611j 0.+22.88245611j 0. +8.74032049j 0. -8.74032049j]
      poles: [-1.8015419 +11.10160138j -1.8015419 -11.10160138j  -2.84848785-17.55317302j -2.84848785+17.55317302j]
      */
      val a = PolynomialSupport.calculateCoefficients(transformedPoles).map(_.real)
      val b = PolynomialSupport.calculateCoefficients(transformedZeros).map(_.real)

      val k = pow(-1, order) * poles.product/zeros.product * pow(bandWidth, poles.size - zeros.size)
      (b.map(_ * k.real), a)

    }

  }

}