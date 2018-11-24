package com.sparja.syto.filter.chebyshev

import breeze.math.Complex
import breeze.numerics.{cos, sin}
import com.sparja.syto.filter.core.Approximation
import org.apache.commons.math3.util.FastMath

class ChebyshevSecondTypeApproximation(order: Int, rp: Float) extends Approximation{

  val eps = Math.sqrt(Math.pow(10, 0.1 * rp) - 1.0)
  val mu =  FastMath.asinh(eps) / order

  override def normalizedPoles = (1 to order)
    .map(k => (2 * k - 1) * Math.PI / (2 * order))
    .map(theta => Complex(r(theta), im(theta))).toList

  private def r(theta: Double) = -sin(theta) * Math.sinh(mu) / (cos(theta) * Math.cosh(mu) * cos(theta) * Math.cosh(mu) + sin(theta) * Math.sinh(mu) * sin(theta) * Math.sinh(mu))

  private def im(theta: Double) = cos(theta) * Math.cosh(mu) / (cos(theta) * Math.cosh(mu) * cos(theta) * Math.cosh(mu) + sin(theta) * Math.sinh(mu) * sin(theta) * Math.sinh(mu))
}
