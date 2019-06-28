package com.sparja.syto.math

import breeze.math.Complex
import junit.framework.TestCase.assertEquals
import org.junit.Test


// [ 1.         -2.0286      1.47628449 -0.37147469]
// (2.0286-0j)
// (1.47628449+0j)
// (-0.371474694+0j)

class PolynomialSupportTest {
  @Test
  def shouldFindCoefficientsByRoots() = {
    val roots = List(Complex(0.7143, 0.33), Complex(0.6, 0), Complex(0.7143, -0.33))
    val coefficients = Polynomial.calculateCoefficients(roots)
    assertEquals(coefficients.head.real, 1, 0.01)
    assertEquals(coefficients(1).real, -2.0286, 0.01)
    assertEquals(coefficients(2).real, 1.4762, 0.01)
    assertEquals(coefficients(3).real, -0.37147, 0.01)
  }

  @Test
  def shouldReturnOneForEmptyRoots() = {
    val roots = List.empty[Complex]
    val coefficients = Polynomial.calculateCoefficients(roots)
    assertEquals(coefficients.head.real, 1, 0.01)
    assertEquals(coefficients.size, 1)

  }

  @Test
  def shouldMultiplyCoefficient() = {
    val z = Coefficient(1.0, 0.0, 1)
    val p = Coefficient(2.0, 0.5, 0)
    val pz = z.multiply(p)

    assertEquals(pz.real, 2.0)
    assertEquals(pz.imaginary, 0.5)
    assertEquals(pz.degree, 1)
  }

  @Test
  def shouldAddCoefficients() = {
    val z = Coefficient(1.0, 0.0, 1)
    val p = Coefficient(2.0, 0.5, 1)
    val pz = z.add(p)

    assertEquals(pz.real, 3.0)
    assertEquals(pz.imaginary, 0.5)
    assertEquals(pz.degree, 1)
  }

}


