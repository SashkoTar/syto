package com.sparja.syto.polynomial

import breeze.math.Complex
import com.sparja.syto.polynomial.root.WeierstrassRootFinder
import com.sparja.syto.util.ComplexAssertion
import org.junit.Test


class PolynomialRootFindingTest {

  @Test
  def shouldCalculate3() = {
     val expected = List(Complex(-0.2846, -0.2715), Complex(-0.4306, -0.0001), Complex(-0.2846, 0.2715))
    val coefficients = List(Coefficient(15.0, 0.0, 3), Coefficient(15, 0.0, 2), Coefficient(6, 0.0, 1), Coefficient(1, 0.0, 0))
    val roots = Polynomial(coefficients).findRoots
    ComplexAssertion.assertContainAll(roots, expected)
  }


  @Test
  def shouldCalculate4() = {
    val expected = List(Complex(-0.1831, -0.2313), Complex(-0.3168, -0.0948), Complex(-0.1831, 0.2313), Complex(-0.3168, 0.0948))
    val coefficients = List(Coefficient(105.0, 0.0, 4), Coefficient(105, 0.0, 3), Coefficient(45, 0.0, 2), Coefficient(10, 0.0, 1), Coefficient(1, 0.0, 0))
    val roots = Polynomial(coefficients).findRoots
    ComplexAssertion.assertContainAll(roots, expected)
  }

  @Test
  def shouldCalculate5() = {
    val expected = List(Complex(-0.12803, -0.1966), Complex(-0.2348, -0.12209), Complex(-0.2742, 0), Complex(-0.12803, 0.1966), Complex(-0.2348, 0.12209))
    val pol = BesselPolynomial.calculate(5)
    val roots = pol.findRoots
    ComplexAssertion.assertContainAll(roots, expected)
  }

  @Test
  def shouldCalculate6() = {
     val expected = List(Complex(-0.0948, -0.1694), Complex(-0.1791, -0.1259), Complex(-0.2259, -0.0461),
      Complex(-0.0948, 0.1694), Complex(-0.1791, 0.1259), Complex(-0.2259, 0.0461))
    val pol = BesselPolynomial.calculate(6)
    val roots = pol.findRoots
    ComplexAssertion.assertContainAll(roots, expected)
  }


}
