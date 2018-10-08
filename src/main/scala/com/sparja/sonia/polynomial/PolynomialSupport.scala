package com.sparja.sonia.polynomial

import org.apache.commons.math3.complex.Complex

object PolynomialSupport {
  def calculateCoefficients(roots: List[Complex]): List[Complex] = {
    val z = Coefficient(1.0, 0.0, 1)
    val r = roots.map(_.negate()).map(root => Coefficient(root.getReal, root.getImaginary, 0))
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
    val coefficients = multiply(List(z, r.head), r.tail)
    coefficients.map(coef => new Complex(coef.real, coef.imaginary))
  }

}
