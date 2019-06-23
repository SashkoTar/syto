package com.sparja.syto.polynomial

import junit.framework.TestCase.assertEquals
import org.junit.Test

class PolynomialTest {

  @Test
  def shouldCalculate() = {
    //f(x) = x*x + x
    val coefficients = List(Coefficient(1.0, 0.0, 2), Coefficient(1.0, 0.0, 1))
    val polynomial = Polynomial(coefficients)
    assertEquals(polynomial.v(1.0), 2.0, 0.001)
    assertEquals(polynomial.v(2.0), 6, 0.001)
    assertEquals(polynomial.v(-2.0), 2, 0.001)
  }

  @Test
  def shouldCalculate2() = {
    //f(x) = 4*x*x*x*x - 0.5*x*x + 3*x - 11
    val coefficients = List(Coefficient(4.0, 0.0, 4), Coefficient(-0.5, 0.0, 2), Coefficient(3, 0.0, 1), Coefficient(-11, 0.0, 0) )

    val polynomial = Polynomial(coefficients)
    assertEquals(polynomial.v(0), -11, 0.001)
    assertEquals(polynomial.v(-2), 45, 0.001)
    assertEquals(polynomial.v(3), 317.5, 0.001)
    //println(polynomial)
  }

}
