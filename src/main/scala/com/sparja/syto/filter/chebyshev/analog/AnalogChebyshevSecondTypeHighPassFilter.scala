package com.sparja.syto.filter.chebyshev.analog

import breeze.math.Complex
import breeze.numerics.{cos, sin}
import com.sparja.syto.filter.core.TransferFunction
import com.sparja.syto.polynomial.PolynomialSupport
import org.apache.commons.math3.util.FastMath

object AnalogChebyshevSecondTypeHighPassFilter {

  def apply(order: Int, rp: Float, cutoffFrequency: Float) =
    new AnalogSecondTypeHighPassTransferFunction(order, rp, cutoffFrequency)


  class AnalogSecondTypeHighPassTransferFunction(order: Int, rp: Float,  cutoffFrequency: Float) extends TransferFunction {

    val zeros = {
      val n = if (order%2 == 0) order / 2 else (order - 1) / 2
      val first =  (1 to n).map(k => (2 * k - 1) * Math.PI / (2 * order))
        .map(theta => Complex.i/cos(theta)).map(cutoffFrequency/ _ ).toList

      first:::first.map(_.conjugate)

    }

     val poles = {
      val eps = Math.sqrt(Math.pow(10, 0.1 * rp) - 1.0)
      val mu =  FastMath.asinh(eps) / order

      def r(theta: Double) = -sin(theta) * Math.sinh(mu) / (cos(theta) * Math.cosh(mu) * cos(theta) * Math.cosh(mu) + sin(theta) * Math.sinh(mu) * sin(theta) * Math.sinh(mu))

      def im(theta: Double) = cos(theta) * Math.cosh(mu) / (cos(theta) * Math.cosh(mu) * cos(theta) * Math.cosh(mu) + sin(theta) * Math.sinh(mu) * sin(theta) * Math.sinh(mu))

      (1 to order)
        .map(k => (2 * k - 1) * Math.PI / (2 * order))
        .map(theta => Complex(r(theta), im(theta))).map(cutoffFrequency / _ ).toList

    }

    def calculateCoefficients(): (List[Double], List[Double]) = {
      val a = PolynomialSupport.calculateCoefficients(poles).map(_.real)
      val b = PolynomialSupport.calculateCoefficients(zeros).map(_.real)

      val k = Math.pow(-1, order) * zeros.product/poles.product

      (b, a)

    }

  }

}