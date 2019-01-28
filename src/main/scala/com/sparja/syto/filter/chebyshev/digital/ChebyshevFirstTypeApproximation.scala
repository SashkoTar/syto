package com.sparja.syto.filter.chebyshev.digital

import breeze.math.Complex
import breeze.numerics.{cos, sin}
import com.sparja.syto.filter.core.Approximation
import org.apache.commons.math3.util.FastMath

class ChebyshevFirstTypeApproximation(order: Int, rp: Double) extends Approximation{

  val eps = Math.sqrt(Math.pow(10, 0.1 * rp) - 1.0)
  val mu =  FastMath.asinh(1 / eps) / order

  override def normalizedPoles = (1 to order)
    .map(k => (2 * k - 1) * Math.PI / (2 * order))
    .map(theta => Complex(-sin(theta) * Math.sinh(mu), cos(theta) * Math.cosh(mu))).toList
}
