package com.sparja.syto.filter.core

import breeze.math.Complex
import breeze.numerics.{cos, sin, sqrt}
import org.apache.commons.math3.util.FastMath

object Prototype {

  def butterworth(order: Int) = {
    val poles = (1 to order)
      .map(k => (2 * k - 1) * Math.PI / (2 * order))
      .map(theta => Complex(-sin(theta), cos(theta))).toList
    val zeros = List.empty
    Roots(zeros, poles, 1.0)
  }

    //TODO investigate how use object to improve the code
  //object chebyshevII

  def chebyshevII(order: Int, rp: Double) = {
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
    val scale = Math.pow(-1, order) * poles.product/zeros.product

    Roots(zeros, poles, scale.real)
  }

  def chebyshev(order: Int, rp: Double) = {
    val eps = Math.sqrt(Math.pow(10, 0.1 * rp) - 1.0)
    val mu =  FastMath.asinh(1 / eps) / order

    val zeros = List.empty[Complex]

    val poles = {
      (1 to order)
        .map(k => (2 * k - 1) * Math.PI / (2 * order))
        .map(theta => Complex(-sin(theta) * Math.sinh(mu), cos(theta) * Math.cosh(mu))).toList
    }

    val scale = Math.pow(-1, order) * (if (order%2 == 0) poles.product/sqrt(1 + eps*eps) else poles.product)

    Roots(zeros, poles, scale.real)
  }

}
