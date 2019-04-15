package com.sparja.syto.filter.core

import breeze.math.Complex
import breeze.numerics.{cos, sin, sqrt}
import com.sparja.syto.polynomial.BesselPolynomial
import com.sparja.syto.polynomial.root.WeierstrassRootFinder
import org.apache.commons.math3.util.FastMath

object Prototype {

  def elliptic(order: Int, rs: Double, rp: Double) = {
    Roots(List.empty[Complex], List.empty[Complex], 1.0)
  }

  //TODO extract to specific module
  private[core] def normFactor(p: List[Complex], k: Double): Double = {

    def G(w: Double) = {
      //""" Gain of filter """
      //return abs(k / prod(1j*w - p))
      k / p.map(Complex.i * w - _).product.abs
    }

    def cutoff(w: Double): Double = {
      //""" When gain = -3 dB, return 0 """
      G(w) - 1 / math.sqrt(2)
    }

    def secantMethod(f: (Double) => Double, x0: Double, x1: Double): Double = {
      val x2 = x1 - f(x1) * (x1 - x0) / (f(x1) - f(x0))
      if (math.abs(x2 - x1) < 0.001)
        x2
      else
        secantMethod(f, x1, x2)
    }

    secantMethod(cutoff, 1.0, 1.5)

  }


  //TODO extend to find diff normalization - delay, mag, phase
  //  # Phase-matched (1/2 max phase shift at 1 rad/sec)
  //  # Asymptotes are same as Butterworth filter
  //    p = 1/_bessel_zeros(N)
  //    a_last = _falling_factorial(2*N, N) // 2**N
  //    p *= 10**(-math.log10(a_last)/N)
  //    k = 1
  def bessel(order: Int, norm: String = "phase"): Roots = {

    def fallingFactorial(x: Int, n: Int) = (0 until n).map(x - _).product

    val zeros = List.empty
    val pol = BesselPolynomial.calculate(order)
    val roots = WeierstrassRootFinder.solve(pol)
    val a_last = fallingFactorial(2 * order, order) / math.pow(2, order).toInt
    val reversedPoles = roots.map(1 / _)

    norm match {

      case "phase" =>
        val degree = -math.log10(a_last) / order
        val poles = reversedPoles.map(_ * math.pow(10, degree))
        Roots(zeros, poles, 1.0)

      case "delay" =>
        val poles = roots.map(1 / _)
        Roots(zeros, reversedPoles, a_last)

      case "mag" =>
        val factor = normFactor(reversedPoles, a_last)
        val normalizedRoots = reversedPoles.map(_ / factor)
        val k = math.pow(factor, -order) * a_last
        Roots(zeros, normalizedRoots, k)

      case _ => throw new IllegalArgumentException("The parameter 'norm' is incorrect. Must one of [phase, delay, mag]")
    }

  }


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
      val n = if (order % 2 == 0) order / 2 else (order - 1) / 2
      val first = (1 to n).map(k => (2 * k - 1) * Math.PI / (2 * order))
        .map(theta => Complex.i / cos(theta)).toList
      first ::: first.map(_.conjugate)
    }

    val poles = {
      val eps = Math.sqrt(Math.pow(10, 0.1 * rp) - 1.0)
      val mu = FastMath.asinh(eps) / order

      def r(theta: Double) = -sin(theta) * Math.sinh(mu) / (cos(theta) * Math.cosh(mu) * cos(theta) * Math.cosh(mu) + sin(theta) * Math.sinh(mu) * sin(theta) * Math.sinh(mu))

      def im(theta: Double) = cos(theta) * Math.cosh(mu) / (cos(theta) * Math.cosh(mu) * cos(theta) * Math.cosh(mu) + sin(theta) * Math.sinh(mu) * sin(theta) * Math.sinh(mu))

      (1 to order)
        .map(k => (2 * k - 1) * Math.PI / (2 * order))
        .map(theta => Complex(r(theta), im(theta))).toList

    }
    val scale = Math.pow(-1, order) * poles.product / zeros.product

    Roots(zeros, poles, scale.real)
  }

  def chebyshev(order: Int, rp: Double) = {
    val eps = Math.sqrt(Math.pow(10, 0.1 * rp) - 1.0)
    val mu = FastMath.asinh(1 / eps) / order

    val zeros = List.empty[Complex]

    val poles = {
      (1 to order)
        .map(k => (2 * k - 1) * Math.PI / (2 * order))
        .map(theta => Complex(-sin(theta) * Math.sinh(mu), cos(theta) * Math.cosh(mu))).toList
    }

    val scale = Math.pow(-1, order) * (if (order % 2 == 0) poles.product / sqrt(1 + eps * eps) else poles.product)

    Roots(zeros, poles, scale.real)
  }

}
