package com.sparja.syto.polynomial

import breeze.math.Complex


object PolynomialSupport {
  def calculateCoefficients(roots: List[Complex]): List[Complex] = {
    val z = Coefficient(1.0, 0.0, 1)
    val r = roots.map(_.unary_-).map(root => Coefficient(root.real, root.imag, 0))
    def multiply(acc: List[Coefficient], remainingRoots: List[Coefficient]): List[Coefficient] = {
      if (remainingRoots.nonEmpty) {
        val zacc = acc.map(_.multiply(z))
        val pacc = acc.map(_.multiply(remainingRoots.head))
        multiply(zacc:::pacc, remainingRoots.tail)
      }
      else
        acc.groupBy(_.degree)
          .mapValues(_.reduce((a, b) => a.add(b))).values.toList.sortWith(_.degree > _.degree)
    }
    //TODO Review this calculations
    val coefficients = if (r.isEmpty) List(z) else multiply(List(z, r.head), r.tail)
    coefficients.map(coef => Complex(coef.real, coef.imaginary))
  }

}
