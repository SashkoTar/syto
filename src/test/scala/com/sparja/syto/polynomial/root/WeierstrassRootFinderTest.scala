package com.sparja.syto.polynomial.root

import breeze.math.Complex
import com.sparja.syto.polynomial.{BesselPolynomial, Coefficient, Polynomial}
import com.sparja.syto.util.ComplexAssertion
import org.junit.Test


class WeierstrassRootFinderTest {

 // @Test
  def shouldCalculate3() = {
    // 1 + 6x + 15x^2 + 15x^3
    // [(-0.2846855768838879-0.2715998514163076j), (-0.43062884623222436+0j), (-0.2846855768838879+0.2715998514163076j)]
    val expected = List(Complex(-0.2846, -0.2715), Complex(-0.4306, -0.0001), Complex(-0.2846, 0.2715))
    val coefficients = List(Coefficient(15.0, 0.0, 3), Coefficient(15, 0.0, 2), Coefficient(6, 0.0, 1), Coefficient(1, 0.0, 0))
    val pol = Polynomial(coefficients)
    val roots = WeierstrassRootFinder.solve(pol)
    println(roots)
    ComplexAssertion.assertContainAll(roots, expected)
  }


  @Test
  def shouldCalculate4() = {
    //1 + 10x + 45x^2 + 105x^3 + 105x^4
    // (-0.1831324805314353-0.23132522602625527j), (-0.31686751946856473-0.09488202514221689j),
    // (-0.31686751946856473+0.09488202514221689j), (-0.1831324805314353+0.23132522602625527j)
    val expected = List(Complex(-0.1831, -0.2313), Complex(-0.3168, -0.0948), Complex(-0.1831, 0.2313), Complex(-0.3168, 0.0948))
    val coefficients = List(Coefficient(105.0, 0.0, 4), Coefficient(105, 0.0, 3), Coefficient(45, 0.0, 2), Coefficient(10, 0.0, 1), Coefficient(1, 0.0, 0))
    val pol = Polynomial(coefficients)
    val roots = WeierstrassRootFinder.solve(pol)
    ComplexAssertion.assertContainAll(roots, expected)
  }

  @Test
  def shouldCalculate5() = {
    //1 + 15x + 105x^2 + 420x^3 + 945x^4 + 945x^5
    //[(-0.12803-0.1966j), (-0.2348-0.12209j), (-0.2742+0j), (-0.2348+0.12209j), (-0.12803+0.1966j)]
    // val coefficients = List(Coefficient(945.0, 0.0, 5), Coefficient(945.0, 0.0, 4), Coefficient(420, 0.0, 3), Coefficient(105, 0.0, 2), Coefficient(15, 0.0, 1), Coefficient(1, 0.0, 0))
    val expected = List(Complex(-0.12803, -0.1966), Complex(-0.2348, -0.12209), Complex(-0.2742, 0), Complex(-0.12803, 0.1966), Complex(-0.2348, 0.12209))
    val pol = BesselPolynomial.calculate(5)
    val roots = WeierstrassRootFinder.solve(pol)
    ComplexAssertion.assertContainAll(roots, expected)
  }

  @Test
  def shouldCalculate6() = {
    //[-0.09489062-0.16944515j -0.17914641-0.12594325j -0.22596297-0.04614136j
    // -0.22596297+0.04614136j -0.17914641+0.12594325j -0.09489062+0.16944515j]
    val expected = List(Complex(-0.0948, -0.1694), Complex(-0.1791, -0.1259), Complex(-0.2259, -0.0461),
      Complex(-0.0948, 0.1694), Complex(-0.1791, 0.1259), Complex(-0.2259, 0.0461))
    val pol = BesselPolynomial.calculate(6)
    val roots = WeierstrassRootFinder.solve(pol)
    ComplexAssertion.assertContainAll(roots, expected)
  }


}
